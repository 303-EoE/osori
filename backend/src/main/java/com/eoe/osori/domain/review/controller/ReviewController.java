package com.eoe.osori.domain.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.domain.review.service.ReviewService;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

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
	 * @return ResponseEntity<EnvelopeResponse<CommonIdResponseDto>>
	 * @see ReviewService
	 */
	@PostMapping()
	public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> registerReview(@RequestBody PostReviewRequestDto postReviewRequestDto, List<MultipartFile> multipartFileList) {

		// Multipartfile 처리해야되는데!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonIdResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.saveReview(postReviewRequestDto))
				.build());
	}

	/**
	 *
	 * 리뷰 삭제
	 * 
	 * @param reviewId Long
	 * @return ResponseEntity<EnvelopeResponse<Void>>
	 * @see ReviewService
	 */
	@DeleteMapping()
	public ResponseEntity<EnvelopeResponse<Void>> deleteReview(@RequestParam("review_id") Long reviewId) {

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
	 * @return ResponseEntity<EnvelopeResponse<GetReviewDetailResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/detail")
	public ResponseEntity<EnvelopeResponse<GetReviewDetailResponseDto>> getReviewDetail(@RequestParam Long reviewId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetReviewDetailResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewDetail(reviewId))
				.build());
	}
}
