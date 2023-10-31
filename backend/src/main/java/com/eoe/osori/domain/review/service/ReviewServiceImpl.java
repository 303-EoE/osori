package com.eoe.osori.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eoe.osori.domain.review.domain.Review;
import com.eoe.osori.domain.review.dto.GetMemberResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
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

	/**
	 *
	 * 리뷰 등록
	 *
	 * @param postReviewRequestDto PostReviewRequestDto
	 * @return CommonIdResponseDto
	 * @see ReviewRepository
	 */
	@Transactional
	@Override
	public CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto) {
		if (postReviewRequestDto.findEmptyValue()) {
			throw new ReviewException(ReviewErrorInfo.INVALID_REVIEW_REQUEST_DATA_ERROR);
		}

		if (reviewRepository.existsByStoreIdAndPaidAt(postReviewRequestDto.getStoreId(),
			postReviewRequestDto.getPaidAt())) {
			// 에러
			throw new ReviewException(ReviewErrorInfo.DUPLICATE_RECEIPT_REQUEST_ERROR);
		}

		Review review = Review.from(postReviewRequestDto);

		review.updateAverageCost(review.getTotalPrice(), review.getFactor(), review.getHeadcount());

		/*
		지울거
		 */
		review.setMemberId(1L);
		/*
		지워!!!!!!!!!!!!!!!!!!!!!
		 */

		reviewRepository.save(review);

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

		// 사진 삭제 로직 추가해야됨!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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

		// 외부 통신 로직 구현해서 가게하고 멤버 정보 받아오기....

		GetStoreResponseDto getStoreResponseDto = new GetStoreResponseDto(1L, "명동 칼국수", "서울시", "강남구");
		GetMemberResponseDto getMemberResponseDto = new GetMemberResponseDto(1L, "디헤");

		// 리뷰 상세 조회 정보 보내기
		return GetReviewDetailResponseDto.of(review, getStoreResponseDto, getMemberResponseDto);
	}
}
