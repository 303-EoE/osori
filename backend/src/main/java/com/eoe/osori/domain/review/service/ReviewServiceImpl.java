package com.eoe.osori.domain.review.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.domain.Review;
import com.eoe.osori.domain.review.domain.ReviewImage;
import com.eoe.osori.domain.review.dto.GetMemberResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
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
	public CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto, List<MultipartFile> reviewImages) {
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


		// 일단 프론트에서 List<MultipartFile> 받은 거 고대로 api 통신해서 image 처리하는 데로 보내주고
		// 그걸 다시 api 통신으로 List<String> reviewImageList 받고 (이미지 url 리스트들만 들어옴)
		// 이걸 reviewImage 엔티티랑 연결해서 저장
		// 이걸 통신으로 보내고
		// List<MultipartFile> reviewImageList = reviewImages;

		// 통신 구현해서 받자
		List<String> reviewImageUrlList = new ArrayList<>();

		reviewImageUrlList.add("https://avatars.githubusercontent.com/u/118112177?v=4");
		reviewImageUrlList.add("https://avatars.githubusercontent.com/u/122416904?v=4");

		for (int i = 0; i < reviewImageUrlList.size(); i++) {
			String imageUrl = reviewImageUrlList.get(i);

			ReviewImage reviewImage = ReviewImage.of(review.getId(), imageUrl);

			reviewImageRepository.save(reviewImage);
		}


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

		reviewImageRepository.deleteAllByReviewId(reviewId);

		// develop/images랑 통신해서 S3에 있는 이미지들도 삭제하는 로직 구현해야 함!!!!!!!!!
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
