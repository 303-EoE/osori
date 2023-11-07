package com.eoe.osori.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eoe.osori.domain.review.domain.ReviewFeed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StoreReviewFeedElement {

	private Long id;
	private LocalDateTime createdAt;
	private Integer averageCost;
	private String content;
	private Double rate;
	private String billType;
	private String memberNickname;
	private String memberProfileImageUrl;
	private String image;

	public static StoreReviewFeedElement from(ReviewFeed reviewFeed) {
		return StoreReviewFeedElement.builder()
			.id(Long.parseLong(reviewFeed.getId()))
			.createdAt(reviewFeed.getCreatedAt())
			.averageCost(reviewFeed.getAverageCost())
			.content(reviewFeed.getContent())
			.rate(reviewFeed.getRate())
			.billType(reviewFeed.getBillType())
			.memberNickname(reviewFeed.getMemberNickname())
			.memberProfileImageUrl(reviewFeed.getMemberProfileImageUrl())
			.image(reviewFeed.getImages().get(0))
			.build();
	}
}
