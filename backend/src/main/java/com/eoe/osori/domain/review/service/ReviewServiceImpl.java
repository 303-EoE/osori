package com.eoe.osori.domain.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.domain.LikeReview;
import com.eoe.osori.domain.review.domain.Review;
import com.eoe.osori.domain.review.domain.ReviewFeed;
import com.eoe.osori.domain.review.domain.ReviewImage;
import com.eoe.osori.domain.review.dto.CommonReviewListResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreReviewCacheDataResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreReviewListResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.domain.review.repository.LikeReviewRepository;
import com.eoe.osori.domain.review.repository.ReviewFeedRepository;
import com.eoe.osori.domain.review.repository.ReviewImageRepository;
import com.eoe.osori.domain.review.repository.ReviewRepository;
import com.eoe.osori.global.advice.error.exception.ReviewException;
import com.eoe.osori.global.advice.error.info.ReviewErrorInfo;
import com.eoe.osori.global.common.api.images.ImageApi;
import com.eoe.osori.global.common.api.images.dto.PostImageResponseDto;
import com.eoe.osori.global.common.api.store.StoreApi;
import com.eoe.osori.global.common.api.store.dto.GetStoreDetailResponseDto;
import com.eoe.osori.global.common.redis.domain.StoreInfo;
import com.eoe.osori.global.common.redis.repository.StoreInfoRedisRepository;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewImageRepository reviewImageRepository;
	private final LikeReviewRepository likeReviewRepository;
	private final ReviewFeedRepository reviewFeedRepository;
	private final StoreInfoRedisRepository storeInfoRedisRepository;
	private final StoreApi storeApi;
	private final ImageApi imageApi;

	/**
	 *
	 * 리뷰 등록
	 *
	 * @param postReviewRequestDto PostReviewRequestDto
	 * @param reviewImages List<MultipartFile>
	 * @return CommonIdResponseDto
	 * @see ReviewRepository
	 * @see ReviewImageRepository
	 * @see ReviewFeedRepository
	 * @see StoreInfoRedisRepository
	 * @see StoreApi
	 * @see ImageApi
	 */
	@Transactional
	@Override
	public CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto, List<MultipartFile> reviewImages) {
		// 입력 데이터 null값 검증
		if (postReviewRequestDto.findEmptyValue()) {
			throw new ReviewException(ReviewErrorInfo.INVALID_REVIEW_REQUEST_DATA_ERROR);
		}

		// 리뷰 이미지 null값 검증
		if (reviewImages == null || reviewImages.isEmpty()) {
			throw new ReviewException(ReviewErrorInfo.INVALID_REVIEW_IMAGES_DATA_ERROR);
		}

		// 가게 기준 중복 영수증 검증
		if (reviewRepository.existsByStoreIdAndPaidAt(postReviewRequestDto.getStoreId(),
			postReviewRequestDto.getPaidAt())) {
			throw new ReviewException(ReviewErrorInfo.DUPLICATE_RECEIPT_REQUEST_ERROR);
		}

		Review review = Review.from(postReviewRequestDto);
		review.updateAverageCost(review.getTotalPrice(), review.getFactor(), review.getHeadcount());
		reviewRepository.save(review);

		// 일단 프론트에서 List<MultipartFile> 받은 거(List<MultipartFile> reviewImageList = reviewImages)
		// 그대로 api 통신해서 image 처리하는 데로 보내주고
		// 그걸 다시 api 통신으로 List<String> reviewImageURLList로 받고 (이미지 url 리스트들만 들어옴)
		// 이걸 reviewImage 엔티티랑 연결해서 저장

		EnvelopeResponse<PostImageResponseDto> postImageResponseDtoEnvelopeResponse;

		try {
			postImageResponseDtoEnvelopeResponse = imageApi.getReviewImages(reviewImages);
		} catch (FeignException e) {
			throw new ReviewException(ReviewErrorInfo.FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST);
		}

		PostImageResponseDto postImageResponseDto = postImageResponseDtoEnvelopeResponse.getData();

		List<String> reviewImageUrlList = postImageResponseDto.getPath().stream()
			.map(imagePathElement -> imagePathElement.getUploadFilePath())
			.collect(Collectors.toList());

		for (String imageUrl : reviewImageUrlList) {
			ReviewImage reviewImage = ReviewImage.of(review.getId(), imageUrl);
			reviewImageRepository.save(reviewImage);
		}

		EnvelopeResponse<GetStoreDetailResponseDto> getStoreDetailResponseDtoEnvelopeResponse;

		try {
			getStoreDetailResponseDtoEnvelopeResponse = storeApi.getStoreDetail(review.getStoreId());
		} catch (FeignException e) {
			throw new ReviewException(ReviewErrorInfo.FAIL_TO_STORE_FEIGN_CLIENT_REQUEST);
		}

		GetStoreDetailResponseDto getStoreResponseDto = getStoreDetailResponseDtoEnvelopeResponse.getData();

		reviewFeedRepository.save(ReviewFeed.of(review, postReviewRequestDto, getStoreResponseDto, reviewImageUrlList));

		// redis cache 확인 및 데이터 업데이트
		Optional<StoreInfo> optionalStoreInfo = storeInfoRedisRepository.findById(review.getStoreId());

		if (optionalStoreInfo.isPresent()) {
			StoreInfo storeInfo = optionalStoreInfo.get();

			storeInfo.addTotalRate(review.getRate());
			storeInfo.plusTotalReviewCount();
			storeInfo.calcAverageRate(storeInfo.getTotalRate(), storeInfo.getTotalReviewCount());

			if (review.getBillType().getName().equals(getStoreResponseDto.getDefaultBillType())) {
				storeInfo.addBillTypeTotalPrice(review.getAverageCost());
				storeInfo.plusBillTypeTotalReviewCount();
				storeInfo.calcAveragePrice(storeInfo.getBillTypeTotalPrice(), storeInfo.getBillTypeTotalReviewCount());
			}

			storeInfoRedisRepository.save(storeInfo);
		}

		return CommonIdResponseDto.from(review.getId());
	}

	/**
	 *
	 * 리뷰 삭제
	 *
	 * @param reviewId Long
	 * @param memberId Long
	 * @see LikeReviewRepository
	 * @see ReviewRepository
	 * @see ReviewFeedRepository
	 */
	@Transactional
	@Override
	public void deleteReview(Long reviewId, Long memberId) {

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID));

		if (!review.getMemberId().equals(memberId)) {
			throw new ReviewException(ReviewErrorInfo.NOT_MATCH_REVIEW_BY_MEMBERID);
		}

		ReviewFeed reviewFeed = reviewFeedRepository.findById(Long.toString(reviewId))
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEWFEED_BY_ID));

		likeReviewRepository.deleteAllByReviewId(reviewId);
		reviewRepository.delete(review);
		reviewFeedRepository.delete(reviewFeed);
	}

	/**
	 *
	 * 리뷰 상세조회
	 *
	 * @param reviewId Long
	 * @param memberId Long
	 * @return GetReviewDetailResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Transactional
	@Override
	public GetReviewDetailResponseDto getReviewDetail(Long reviewId, Long memberId) {
		// 리뷰 아이디 기준으로 리뷰 상세 조회 -> 에러

		ReviewFeed reviewFeed = reviewFeedRepository.findById(Long.toString(reviewId))
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEWFEED_BY_ID));

		Boolean liked = likeReviewRepository.existsByReviewIdAndMemberId(reviewId, memberId);

		Boolean isMine = memberId.equals(reviewFeed.getMemberId());

		// 리뷰 상세 조회 정보 보내기
		return GetReviewDetailResponseDto.of(reviewFeed, liked, isMine);
	}

	/**
	 *
	 * 좋아요 등록 / 취소
	 *
	 * @param reviewId Long
	 * @param memberId Long
	 * @see ReviewRepository
	 * @see LikeReviewRepository
	 */
	@Transactional
	@Override
	public void likeOrDisLikeReivew(Long reviewId, Long memberId) {

		if (!reviewRepository.existsById(reviewId)) {
			throw new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID);
		}

		if (likeReviewRepository.existsByReviewIdAndMemberId(reviewId, memberId)) {
			likeReviewRepository.deleteByReviewIdAndMemberId(reviewId, memberId);
			return;
		}

		likeReviewRepository.save(LikeReview.of(reviewId, memberId));
	}

	/**
	 *
	 * 지역 리뷰 전체 조회
	 *
	 * @param storeDepth1 String
	 * @param storeDepth2 String
	 * @param memberId Long
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getReviewListByRegion(String storeDepth1, String storeDepth2, Long memberId) {

		List<ReviewFeed> reviewFeedList = reviewFeedRepository
			.findAllByStoreDepth1AndStoreDepth2OrderByCreatedAtDesc(storeDepth1, storeDepth2);

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberId(memberId).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		return CommonReviewListResponseDto.ofReviewFeedListAndLikeReviewIdListAndMemberId(reviewFeedList,
			likeReviewIdList, memberId);
	}

	/**
	 *
	 * 가게 리뷰 요약 조회
	 *
	 * @param storeId Long
	 * @return GetStoreReviewListResponseDto
	 * @see ReviewFeedRepository
	 */
	@Override
	public GetStoreReviewListResponseDto getReviewListByStore(Long storeId) {

		List<ReviewFeed> reviewFeedList = reviewFeedRepository.findAllByStoreIdOrderByCreatedAtDesc(storeId);

		return GetStoreReviewListResponseDto.from(reviewFeedList);
	}

	/**
	 *
	 * 멤버 리뷰 전체 조회
	 *
	 * @param memberId Long
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getMemberReviewList(Long memberId) {

		List<ReviewFeed> reviewFeedList = reviewFeedRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberId(memberId).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		return CommonReviewListResponseDto.ofReviewFeedListAndLikeReviewIdListAndMemberId(reviewFeedList,
			likeReviewIdList, memberId);
	}

	/**
	 *
	 * 좋아요한 리뷰 전체 조회
	 *
	 * @param memberId Long
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getLikeReviewList(Long memberId) {

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberIdOrderByIdDesc(memberId).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		List<ReviewFeed> reviewFeedList = new ArrayList<>();

		for (Long likeReviewId : likeReviewIdList) {
			ReviewFeed reviewFeed = reviewFeedRepository.findById(Long.toString(likeReviewId))
				.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEWFEED_BY_ID));

			reviewFeedList.add(reviewFeed);
		}

		return CommonReviewListResponseDto.ofReviewFeedListAndAndMemberId(reviewFeedList, memberId);
	}

	/**
	 *  가게 리뷰 캐시 데이터 조회
	 *
	 * @param storeId Long
	 * @param defaultBillType String
	 * @return GetStoreReviewCacheDataResponseDto
	 * @see ReviewRepository
	 */
	@Override
	public GetStoreReviewCacheDataResponseDto getReviewCacheDataByStore(Long storeId, String defaultBillType) {

		StoreInfo storeInfo = StoreInfo.from(storeId);

		List<Review> reviewList = reviewRepository.findAllByStoreId(storeId);

		for (Review review : reviewList) {
			storeInfo.addTotalRate(review.getRate());
			storeInfo.plusTotalReviewCount();

			if (review.getBillType().getName().equals(defaultBillType)) {
				storeInfo.addBillTypeTotalPrice(review.getAverageCost());
				storeInfo.plusBillTypeTotalReviewCount();
			}
		}

		storeInfo.calcAveragePrice(storeInfo.getBillTypeTotalPrice(), storeInfo.getBillTypeTotalReviewCount());
		storeInfo.calcAverageRate(storeInfo.getTotalRate(), storeInfo.getTotalReviewCount());

		return GetStoreReviewCacheDataResponseDto.from(storeInfo);
	}

}
