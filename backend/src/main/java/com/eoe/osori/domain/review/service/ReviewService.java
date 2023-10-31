package com.eoe.osori.domain.review.service;

import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface ReviewService {

	CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto);
	
	// 후에 멤버 검증 로직 추가
	void deleteReview(Long reviewId, Long memberId);

	GetReviewDetailResponseDto getReviewDetail(Long reviewId);
}
