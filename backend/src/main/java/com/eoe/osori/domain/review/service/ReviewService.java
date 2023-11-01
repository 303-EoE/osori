package com.eoe.osori.domain.review.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface ReviewService {

	CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto, List<MultipartFile> reviewImages);
	
	// 후에 멤버 검증 로직 추가
	void deleteReview(Long reviewId, Long memberId);

	GetReviewDetailResponseDto getReviewDetail(Long reviewId);
}