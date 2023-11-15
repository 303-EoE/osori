package com.eoe.osori.domain.review.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.dto.CommonReviewListResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreReviewListResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface ReviewService {

	CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto, List<MultipartFile> reviewImages);

	void deleteReview(Long reviewId, Long memberId);

	GetReviewDetailResponseDto getReviewDetail(Long reviewId, Long memberId);

	void likeOrDisLikeReivew(Long reviewId, Long memberId);

	CommonReviewListResponseDto getReviewListByRegion(String storeDepth1, String storeDepth2, Long memberId);

	GetStoreReviewListResponseDto getReviewListByStore(Long storeId);

	CommonReviewListResponseDto getMemberReviewList(Long memberId);

	CommonReviewListResponseDto getLikeReviewList(Long memberId);

}
