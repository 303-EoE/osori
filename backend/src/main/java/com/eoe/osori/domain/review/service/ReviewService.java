package com.eoe.osori.domain.review.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.domain.review.dto.CommonReviewListResponseDto;
import com.eoe.osori.domain.review.dto.GetReviewDetailResponseDto;
import com.eoe.osori.domain.review.dto.GetStoreReviewListResponseDto;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.global.common.api.member.dto.GetMemberResponseDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface ReviewService {

	CommonIdResponseDto saveReview(PostReviewRequestDto postReviewRequestDto, List<MultipartFile> reviewImages);

	void deleteReview(Long reviewId);

	GetReviewDetailResponseDto getReviewDetail(Long reviewId);

	void likeOrDisLikeReivew(Long reviewId);

	CommonReviewListResponseDto getReviewListByRegion(String storeDepth1, String storeDepth2);

	GetStoreReviewListResponseDto getReviewListByStore(Long storeId);

	CommonReviewListResponseDto getMyReviewList();

	CommonReviewListResponseDto getOtherReviewList(Long memberId);

	CommonReviewListResponseDto getLikeReviewList();

	GetMemberResponseDto getLoginMember();
}
