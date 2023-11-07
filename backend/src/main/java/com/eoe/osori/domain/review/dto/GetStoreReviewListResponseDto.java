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
public class GetStoreReviewListResponseDto {

	List<StoreReviewFeedElement> reviews;

	public static GetStoreReviewListResponseDto from(List<ReviewFeed> reviewFeedList) {
		return GetStoreReviewListResponseDto.builder()
			.reviews(reviewFeedList.stream()
				.map(reviewFeed -> StoreReviewFeedElement.from(reviewFeed))
				.collect(Collectors.toList()))
			.build();
	}
}
