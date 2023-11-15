package com.eoe.osori.domain.review.controller;

import java.util.List;

import com.eoe.osori.global.common.receipt.dto.PostReceiptResponseDto;
import com.eoe.osori.global.common.receipt.service.ReceiptService;
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
import com.eoe.osori.domain.review.dto.GetStoreReviewListResponseDto;
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
	private final ReceiptService receiptService;

	/**
	 *
	 * 리뷰 등록
	 *
	 * @param postReviewRequestDto PostReviewRequestDto
	 * @param reviewImages List<MultipartFile>
	 * @return ResponseEntity<EnvelopeResponse<CommonIdResponseDto>>
	 * @see ReviewService
	 */
	@PostMapping()
	public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> registerReview(
		@RequestPart(value = "postReviewRequestDto") PostReviewRequestDto postReviewRequestDto,
		@RequestPart(value = "reviewImages", required = false) List<MultipartFile> reviewImages) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonIdResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.saveReview(postReviewRequestDto, reviewImages))
				.build());
	}

	/**
	 *
	 * 리뷰 삭제
	 *
	 * @param reviewId Long
	 * @param memberId Long
	 * @return ResponseEntity<EnvelopeResponse<Void>>
	 * @see ReviewService
	 */
	@DeleteMapping()
	public ResponseEntity<EnvelopeResponse<Void>> deleteReview(@RequestParam("review_id") Long reviewId,
	@RequestParam("member_id") Long memberId) {
		
		reviewService.deleteReview(reviewId, memberId);

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
	 * @param memberId Long
	 * @return ResponseEntity<EnvelopeResponse<GetReviewDetailResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/detail")
	public ResponseEntity<EnvelopeResponse<GetReviewDetailResponseDto>> getReviewDetail(
		@RequestParam("review_id") Long reviewId, @RequestParam("member_id") Long memberId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetReviewDetailResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewDetail(reviewId, memberId))
				.build());
	}

	/**
	 *
	 * 리뷰 좋아요 / 취소
	 *
	 * @param reviewId
	 * @param memberId
	 * @return ResponseEntity<EnvelopeResponse<Void>>
	 * @see ReviewService
	 */
	@PostMapping("/like")
	public ResponseEntity<EnvelopeResponse<Void>> likeOrDislikeReview(@RequestParam("review_id") Long reviewId,
		@RequestParam("member_id") Long memberId) {

		reviewService.likeOrDisLikeReivew(reviewId, memberId);

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
	 * @param memberId Long
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/region")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getReviewListByRegion
	(@RequestParam("depth1") String storeDepth1, @RequestParam("depth2") String storeDepth2,
		@RequestParam("member_id") Long memberId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewListByRegion(storeDepth1, storeDepth2, memberId))
				.build());
	}

	/**
	 *
	 * 가게 리뷰 요약 조회
	 *
	 * @param storeId Long
	 * @return ResponseEntity<EnvelopeResponse<GetStoreReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/store")
	public ResponseEntity<EnvelopeResponse<GetStoreReviewListResponseDto>> getReviewListByStore
	(@RequestParam("store_id") Long storeId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<GetStoreReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getReviewListByStore(storeId))
				.build());
	}

	/**
	 *
	 * 멤버 리뷰 전체 조회
	 *
	 * @param memberId Long
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/member")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getMemberReviewList
		(@RequestParam("member_id") Long memberId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getMemberReviewList(memberId))
				.build());
	}

	/**
	 *
	 * 좋아요한 리뷰 전체 조회
	 *
	 * @param memberId Long
	 * @return ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>>
	 * @see ReviewService
	 */
	@GetMapping("/like")
	public ResponseEntity<EnvelopeResponse<CommonReviewListResponseDto>> getLikeReviewList
		(@RequestParam("member_id") Long memberId) {
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(EnvelopeResponse.<CommonReviewListResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(reviewService.getLikeReviewList(memberId))
				.build());
	}

	/**
	 *
	 * 영수증 사진 스캔
	 *
	 * @param multipartFile MultipartFile
	 * @return ResponseEntity<EnvelopeResponse<PostReceiptResponseDto>>
	 * @see ReceiptService
	 */
	@PostMapping("/receipt")
	public ResponseEntity<EnvelopeResponse<PostReceiptResponseDto>> getReviewDetail(@RequestPart(required = true) MultipartFile multipartFile) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(EnvelopeResponse.<PostReceiptResponseDto>builder()
						.code(HttpStatus.OK.value())
						.data(receiptService.getReceiptInfo(multipartFile))
						.build());
	}
}
