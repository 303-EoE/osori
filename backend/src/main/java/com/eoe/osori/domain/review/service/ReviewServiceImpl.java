package com.eoe.osori.domain.review.service;

import java.util.ArrayList;
import java.util.List;
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
import com.eoe.osori.global.common.api.member.MemberApi;
import com.eoe.osori.global.common.api.store.StoreApi;
import com.eoe.osori.global.common.api.member.dto.GetMemberResponseDto;
import com.eoe.osori.global.common.api.store.dto.GetStoreDetailResponseDto;
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
	private final StoreApi storeApi;
	private final ImageApi imageApi;
	private final MemberApi memberApi;

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
	 * @see StoreApi
	 * @see ImageApi
	 * @see MemberApi
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

		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		Review review = Review.of(postReviewRequestDto, getMemberResponseDto.getId());
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
			System.out.println(e.getMessage());
			throw new ReviewException(ReviewErrorInfo.FAIL_TO_IMAGE_FEIGN_CLIENT_REQUEST);
		}

		PostImageResponseDto postImageResponseDto = postImageResponseDtoEnvelopeResponse.getData();

		List<String> reviewImageUrlList = postImageResponseDto.getPath().stream()
			.map(imagePathElement -> imagePathElement.getUploadFilePath())
			.collect(Collectors.toList());


		for (int i = 0; i < reviewImageUrlList.size(); i++) {
			String imageUrl = reviewImageUrlList.get(i);
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

		reviewFeedRepository.save(ReviewFeed.of(review, getMemberResponseDto, getStoreResponseDto, reviewImageUrlList));

		return CommonIdResponseDto.from(review.getId());
	}

	/**
	 *
	 * 리뷰 삭제
	 *
	 * @param reviewId Long
	 * @see LikeReviewRepository
	 * @see ReviewRepository
	 * @see ReviewFeedRepository
	 */
	@Transactional
	@Override
	public void deleteReview(Long reviewId) {

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */


		/**
		 * 멤버 통신 구현한 뒤 지울 거!!
		 */
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤",
			"https://avatars.githubusercontent.com/u/122416904?v=4");
		/**
		 * 멤버 통신 구현한 뒤 지울 거!!
		 */

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID));

		if (review.getMemberId() != getMemberResponseDto.getId()) {
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
	 * @param reviewId
	 * @return GetReviewDetailResponseDto
	 * @see ReviewRepository
	 * @see ReviewImageRepository
	 * @see LikeReviewRepository
	 * @see StoreApi
	 * @see MemberApi
	 */
	@Transactional
	@Override
	public GetReviewDetailResponseDto getReviewDetail(Long reviewId) {
		// 리뷰 아이디 기준으로 리뷰 상세 조회 -> 에러

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID));

		/**
		 * 멤버 통신 구현하고 지울 거!!!!!!!!!!!!!!!!!!!!!
		 */
		GetMemberResponseDto getMemberResponseDtoByReview = new GetMemberResponseDto(1L, "디헤",
			"https://avatars.githubusercontent.com/u/122416904?v=4");
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(2L, "의서기",
			"https://avatars.githubusercontent.com/u/122416904?v=4");
		/**
		 * 지울 거!!!!!!!!!!!!!!!!!!!!!
		 */


		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// 로그인한 사용자 데이터 받기 (조회한 게시물이 로그인한 사용자가 작성한 건지 파악하기 위해)
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();

		// 리뷰에 들어갈 멤버 데이터 받기
		// EnvelopeResponse<GetMemberResponseDto> getMemberResponseDtoEnvelopeResponseByReview;
		//
		// try {
		// 	getMemberResponseDtoEnvelopeResponseByReview = memberApi.getOtherMember(review.getMemberId());
		// } catch (FeignException e) {
		// 	throw new ReviewException(ReviewErrorInfo.FAIL_TO_MEMBER_FEIGN_CLIENT_REQUEST);
		// }
		//
		// GetMemberResponseDto getMemberResponseDtoByReview = getMemberResponseDtoEnvelopeResponseByReview.getData();

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		EnvelopeResponse<GetStoreDetailResponseDto> getStoreDetailResponseDtoEnvelopeResponse;

		try {
			getStoreDetailResponseDtoEnvelopeResponse =	storeApi.getStoreDetail(review.getStoreId());
		} catch (FeignException e) {
			throw new ReviewException(ReviewErrorInfo.FAIL_TO_STORE_FEIGN_CLIENT_REQUEST);
		}

		GetStoreDetailResponseDto getStoreResponseDto = getStoreDetailResponseDtoEnvelopeResponse.getData();

		List<ReviewImage> reviewImages = reviewImageRepository.findAllByReviewId(reviewId);

		Boolean liked = likeReviewRepository.existsByReviewIdAndMemberId(reviewId,
			getMemberResponseDto.getId());

		Boolean isMine = (getMemberResponseDto.getId() == review.getMemberId()) ? true : false;

		// 리뷰 상세 조회 정보 보내기
		return GetReviewDetailResponseDto.of(review, reviewImages, getStoreResponseDto,
			getMemberResponseDtoByReview, liked, isMine);
	}

	/**
	 *
	 * 좋아요 등록 / 취소
	 *
	 * @param reviewId Long
	 * @see ReviewRepository
	 * @see LikeReviewRepository
	 */
	@Transactional
	@Override
	public void likeOrDisLikeReivew(Long reviewId) {

		if (!reviewRepository.existsById(reviewId)) {
			throw new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID);
		}

		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		if (likeReviewRepository.existsByReviewIdAndMemberId(reviewId, getMemberResponseDto.getId())) {
			likeReviewRepository.deleteByReviewIdAndMemberId(reviewId, getMemberResponseDto.getId());
			return;
		}

		likeReviewRepository.save(LikeReview.of(reviewId, getMemberResponseDto.getId()));
	}

	/**
	 *
	 * 지역 리뷰 전체 조회
	 *
	 * @param storeDepth1 String
	 * @param storeDepth2 String
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getReviewListByRegion(String storeDepth1, String storeDepth2) {

		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		List<ReviewFeed> reviewFeedList = reviewFeedRepository
			.findAllByStoreDepth1AndStoreDepth2OrderByCreatedAtDesc(storeDepth1, storeDepth2);

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberId(getMemberResponseDto.getId()).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		return CommonReviewListResponseDto.ofReviewFeedListAndLikeReviewIdListAndMemberId(reviewFeedList,
			likeReviewIdList, getMemberResponseDto.getId());
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
	 *  내 리뷰 전체 조회
	 *
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getMyReviewList() {

		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		List<ReviewFeed> reviewFeedList = reviewFeedRepository.findAllByMemberIdOrderByCreatedAtDesc
			(getMemberResponseDto.getId());

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberId
				(getMemberResponseDto.getId()).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		return CommonReviewListResponseDto
			.ofReviewFeedListAndLikeReviewIdListAndMemberId
				(reviewFeedList, likeReviewIdList, getMemberResponseDto.getId());
	}

	/**
	 *
	 * 다른 사람 리뷰 전체 조회
	 *
	 * @param memberId Long
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getOtherReviewList(Long memberId) {

		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		List<ReviewFeed> reviewFeedList = reviewFeedRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberId(getMemberResponseDto.getId()).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		return CommonReviewListResponseDto
			.ofReviewFeedListAndLikeReviewIdListAndMemberId
				(reviewFeedList, likeReviewIdList, getMemberResponseDto.getId());
	}

	/**
	 *
	 * 좋아요한 리뷰 전체 조회
	 *
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 * @see LikeReviewRepository
	 */
	@Override
	public CommonReviewListResponseDto getLikeReviewList() {

		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		// member 정보 통신한 뒤 지울 거 !!!!!!!!!!!!!!!!!!!!!!!!

		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */
		// GetMemberResponseDto getMemberResponseDto = getLoginMember();
		/**
		 *멤버 서비스 구현되면 이거 써야됨!!!!!!!!!!!!!!!
		 */

		List<Long> likeReviewIdList = likeReviewRepository.findAllByMemberIdOrderByIdDesc
				(getMemberResponseDto.getId()).stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());


		List<ReviewFeed> reviewFeedList = new ArrayList<>();

		for (Long likeReviewId : likeReviewIdList) {
			ReviewFeed reviewFeed = reviewFeedRepository.findById(Long.toString(likeReviewId))
				.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEWFEED_BY_ID));

			reviewFeedList.add(reviewFeed);
		}

		return CommonReviewListResponseDto.ofReviewFeedListAndAndMemberId(reviewFeedList, getMemberResponseDto.getId());
	}

	/**
	 *
	 * 로그인한 사용자 데이터 받기
	 *
	 * @return GetMemberResponseDto
	 * @see MemberApi
	 */
	@Override
	public GetMemberResponseDto getLoginMember() {

		EnvelopeResponse<GetMemberResponseDto> getMemberResponseDtoEnvelopeResponse;

		try {
			getMemberResponseDtoEnvelopeResponse = memberApi.getMember();
		} catch (FeignException e) {
			throw new ReviewException(ReviewErrorInfo.FAIL_TO_MEMBER_FEIGN_CLIENT_REQUEST);
		}

		GetMemberResponseDto getMemberResponseDto = getMemberResponseDtoEnvelopeResponse.getData();

		return getMemberResponseDto;
	}

}
