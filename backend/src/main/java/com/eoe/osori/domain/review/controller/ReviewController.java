package com.eoe.osori.domain.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.dto.CommonReviewListResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.domain.review.service.ReviewService;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
	private final ReviewService reviewService;

	/**
	 *
	 * 리뷰 등록
	 *
	 * @param postReviewRequestDto PostReviewRequestDto
	 * @param reviewImages List<MultipartFile>
	 * @return ResponseEntity<EnvelopeResponse < CommonIdResponseDto>>
	 * @see ReviewService
	 */
	@PostMapping()
	public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> registerReview(
		@RequestPart(value = "postReviewRequestDto") PostReviewRequestDto postReviewRequestDto,
		@RequestPart(value = "reviewImages", required = false) List<MultipartFile> reviewImages) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonIdResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.saveReview(postReviewRequestDto, reviewImages, 1L))
				.build());
	}

	/**
	 *
	 * 리뷰 삭제
	 *
	 * @param reviewId Long
	 * @return ResponseEntity<EnvelopeResponse < Void>>
	 * @see ReviewService
	 */
	@DeleteMapping()
	public ResponseEntity<EnvelopeResponse<Void>> deleteReview(@RequestParam("review_id") Long reviewId) {

		// 몽고DB에서도 ReviewFeed 같이 삭제 로직 추가
		
		reviewService.deleteReview(reviewId, 1L);

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<Void>builder()
				.code(HttpStatus.OK.value())
				.build());
	}

	/**
	 *
	 * 리뷰 상세조회
	 *
	 * @param reviewId Long
	 * @return ResponseEntity<EnvelopeResponse < GetReviewDetailResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/detail")
	public ResponseEntity<EnvelopeResponse<GetReviewDetailResponseDto>> getReviewDetail(
		@RequestParam("review_id") Long reviewId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetReviewDetailResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewDetail(reviewId))
				.build());
	}

	/**
	 *
	 * 리뷰 좋아요 / 취소
	 *
	 * @param reviewId
	 * @return ResponseEntity<EnvelopeResponse < Void>>
	 * @see ReviewService
	 */
	@PostMapping("/like")
	public ResponseEntity<EnvelopeResponse<Void>> likeOrDislikeReview(@RequestParam("review_id") Long reviewId) {

		reviewService.likeOrDisLikeReivew(reviewId, 1L);

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<Void>builder()
				.code(HttpStatus.OK.value())
				.build());
	}

	/**
	 *
	 * 지역 리뷰 전체 조회
	 *
	 * @param storeDepth1 String
	 * @param storeDepth2 String
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/region")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getReviewListByRegion
	(@RequestParam("depth1") String storeDepth1, @RequestParam("depth2") String storeDepth2) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewListByRegion(storeDepth1, storeDepth2, 1L))
				.build());
	}

	/**
	 *
	 * 가게 리뷰 요약 조회
	 *
	 * @param storeId Long
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/store")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getReviewListByStore
	(@RequestParam("store_id") Long storeId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewListByStore(storeId))
				.build());
	}

	/**
	 *
	 * 내 리뷰 전체 조회
	 *
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/my-review")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getMyReviewList() {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getMyReviewList(1L))
				.build());
	}

	/**
	 *
	 * 다른 사람 리뷰 전체 조회
	 *
	 * @param memberId Long
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/member")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getOtherReviewList
		(@RequestParam("member_id") Long memberId, Long loginMemberId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getOtherReviewList(memberId, loginMemberId))
				.build());
	}

	/**
	 *
	 * 좋아요한 리뷰 전체 조회
	 *
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/like")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getLikeReviewList() {

		// 로직 테스트하고 고치기
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getLikeReviewList(1L))
				.build());
	}
}
