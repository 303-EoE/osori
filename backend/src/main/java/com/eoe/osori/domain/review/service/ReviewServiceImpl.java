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
import com.eoe.osori.domain.review.dto.GetMemberResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.domain.review.repository.LikeReviewRepository;
import com.eoe.osori.domain.review.repository.ReviewFeedRepository;
import com.eoe.osori.domain.review.repository.ReviewImageRepository;
import com.eoe.osori.domain.review.repository.ReviewRepository;
import com.eoe.osori.global.advice.error.exception.ReviewException;
import com.eoe.osori.global.advice.error.info.ReviewErrorInfo;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

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

	/**
	 *
	 * 리뷰 등록
	 *
	 * @param postReviewRequestDto PostReviewRequestDto
	 * @param reviewImages List<MultipartFile>
	 * @return CommonIdResponseDto
	 * @see ReviewRepository
	 */
	@Transactional
	@Override
	public CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto, List<MultipartFile> reviewImages,
		Long memberId) {
		// 입력 데이터 null값 검증
		if (postReviewRequestDto.findEmptyValue()) {
			throw new ReviewException(ReviewErrorInfo.INVALID_REVIEW_REQUEST_DATA_ERROR);
		}

		// 가게 기준 중복 영수증 검증
		if (reviewRepository.existsByStoreIdAndPaidAt(postReviewRequestDto.getStoreId(),
			postReviewRequestDto.getPaidAt())) {
			throw new ReviewException(ReviewErrorInfo.DUPLICATE_RECEIPT_REQUEST_ERROR);
		}

		Review review = Review.of(postReviewRequestDto, memberId);
		review.updateAverageCost(review.getTotalPrice(), review.getFactor(), review.getHeadcount());
		reviewRepository.save(review);

		// 일단 프론트에서 List<MultipartFile> 받은 거(List<MultipartFile> reviewImageList = reviewImages)
		// 그대로 api 통신해서 image 처리하는 데로 보내주고
		// 그걸 다시 api 통신으로 List<String> reviewImageURLList로 받고 (이미지 url 리스트들만 들어옴)
		// 이걸 reviewImage 엔티티랑 연결해서 저장

		// 통신 구현해서 받자
		List<String> reviewImageUrlList = new ArrayList<>();

		/**
		 * 지워줄 거!!!
		 */
		reviewImageUrlList.add("https://avatars.githubusercontent.com/u/118112177?v=4");
		reviewImageUrlList.add("https://avatars.githubusercontent.com/u/122416904?v=4");
		/**
		 * 지워줄 거!!!
		 */

		for (int i = 0; i < reviewImageUrlList.size(); i++) {
			String imageUrl = reviewImageUrlList.get(i);
			ReviewImage reviewImage = ReviewImage.of(review.getId(), imageUrl);
			reviewImageRepository.save(reviewImage);
		}

		// meber, store 정보 통신해서 받자
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤", "이미지url1");
		GetStoreResponseDto getStoreResponseDto = new GetStoreResponseDto(10L, "명칼", "서울시", "강남구");

		reviewFeedRepository.save(ReviewFeed.of(review, getMemberResponseDto, getStoreResponseDto, reviewImageUrlList));

		return CommonIdResponseDto.from(review.getId());
	}

	/**
	 *
	 * 리뷰 삭제
	 *
	 * @param reviewId Long
	 * @param memberId Long
	 * @see ReviewRepository
	 */
	@Transactional
	@Override
	public void deleteReview(Long reviewId, Long memberId) {
		// 리뷰id 기준으로 리뷰 조회 -> 에러
		// 리뷰에 작성자 id
		// == memberId
		// != 에러

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID));

		if (review.getMemberId() != memberId) {
			throw new ReviewException(ReviewErrorInfo.NOT_MATCH_REVIEW_BY_MEMBERID);
		}

		reviewRepository.delete(review);
	}

	/**
	 *
	 * 리뷰 상세조회
	 *
	 * @param reviewId
	 * @return GetReviewDetailResponseDto
	 * @see ReviewRepository
	 */
	@Transactional
	@Override
	public GetReviewDetailResponseDto getReviewDetail(Long reviewId) {
		// 리뷰 아이디 기준으로 리뷰 상세 조회 -> 에러

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID));

		// 외부 통신 로직 구현해서 가게하고 멤버 정보 받아와서 isMine, like 처리 추가
		/**
		 * 지울 거!!!!!!!!!!!!!!!!!!!!!
		 */
		GetStoreResponseDto getStoreResponseDto = new GetStoreResponseDto(1L, "명동 칼국수", "서울시", "강남구");
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤",
			"https://avatars.githubusercontent.com/u/122416904?v=4");
		/**
		 * 지울 거!!!!!!!!!!!!!!!!!!!!!
		 */

		List<ReviewImage> reviewImages = reviewImageRepository.findAllByReviewId(reviewId);

		// 리뷰 상세 조회 정보 보내기
		return GetReviewDetailResponseDto.of(review, reviewImages, getStoreResponseDto, getMemberResponseDto);
	}

	/**
	 *
	 * 좋아요 등록 / 취소
	 *
	 * @param reviewId Long
	 * @param memberId Long
	 * @see LikeReviewRepository
	 */
	@Transactional
	@Override
	public void likeOrDisLikeReivew(Long reviewId, Long memberId) {

		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new ReviewException(ReviewErrorInfo.NOT_FOUND_REVIEW_BY_ID));

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
	 * @return CommonReviewListResponseDto
	 * @see ReviewFeedRepository
	 */
	@Override
	public CommonReviewListResponseDto getReviewListByRegion(String storeDepth1, String storeDepth2, Long memberId) {

		List<ReviewFeed> reviewFeedList = reviewFeedRepository
			.findAllByStoreDepth1AndStoreDepth2(storeDepth1, storeDepth2);

		List<LikeReview> likeReviewList = likeReviewRepository.findAllByMemberId(memberId);

		List<Long> likeReviewIdList = likeReviewList.stream()
			.map(likeReview -> likeReview.getReviewId())
			.collect(Collectors.toList());

		return CommonReviewListResponseDto.from(reviewFeedList, likeReviewIdList, memberId);
	}
}
