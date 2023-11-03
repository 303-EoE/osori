package com.eoe.osori.domain.review.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.eoe.osori.domain.review.domain.ReviewFeed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonReviewListResponseDto {

	List<ReviewFeedElement> reviews;

	public static CommonReviewListResponseDto from(List<ReviewFeed> reviewFeedList, List<Long> likeReviewIdList,
		Long memberId) {
		return CommonReviewListResponseDto.builder()
			.reviews(reviewFeedList.stream()
				.map(reviewFeed -> ReviewFeedElement.from(reviewFeed, likeReviewIdList, memberId))
				.collect(Collectors.toList()))
			.build();
	}
}
