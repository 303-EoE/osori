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
public class ReviewFeedElement {

	private Long id;
	private LocalDateTime createdAt;
	private Integer averageCost;
	private String content;
	private Double rate;
	private String billType;
	private Long storeId;
	private String storeName;
	private String storeDepth1;
	private String storeDepth2;
	private Long memberId;
	private String memberNickname;
	private String memberProfileImageUrl;
	private List<String> images;
	private Boolean liked;
	private Boolean isMine;

	public static ReviewFeedElement ofReviewFeedAndLikeReviewListAndMemberId
		(ReviewFeed reviewFeed, List<Long> likeReviewIdList, Long memberId) {
		return ReviewFeedElement.builder()
			.id(Long.parseLong(reviewFeed.getId()))
			.createdAt(reviewFeed.getCreatedAt())
			.averageCost(reviewFeed.getAverageCost())
			.content(reviewFeed.getContent())
			.rate(reviewFeed.getRate())
			.billType(reviewFeed.getBillType())
			.storeId(reviewFeed.getStoreId())
			.storeName(reviewFeed.getStoreName())
			.storeDepth1(reviewFeed.getStoreDepth1())
			.storeDepth2(reviewFeed.getStoreDepth2())
			.memberId(reviewFeed.getMemberId())
			.memberNickname(reviewFeed.getMemberNickname())
			.memberProfileImageUrl(reviewFeed.getMemberProfileImageUrl())
			.images(reviewFeed.getImages())
			.liked(likeReviewIdList.contains(Long.parseLong(reviewFeed.getId())))
			.isMine(reviewFeed.getMemberId().equals(memberId))
			.build();
	}

	public static ReviewFeedElement ofReviewFeedAndMemberId(ReviewFeed reviewFeed, Long memberId) {
		return ReviewFeedElement.builder()
			.id(Long.parseLong(reviewFeed.getId()))
			.createdAt(reviewFeed.getCreatedAt())
			.averageCost(reviewFeed.getAverageCost())
			.content(reviewFeed.getContent())
			.rate(reviewFeed.getRate())
			.billType(reviewFeed.getBillType())
			.storeId(reviewFeed.getStoreId())
			.storeName(reviewFeed.getStoreName())
			.storeDepth1(reviewFeed.getStoreDepth1())
			.storeDepth2(reviewFeed.getStoreDepth2())
			.memberId(reviewFeed.getMemberId())
			.memberNickname(reviewFeed.getMemberNickname())
			.memberProfileImageUrl(reviewFeed.getMemberProfileImageUrl())
			.images(reviewFeed.getImages())
			.liked(true)
			.isMine(reviewFeed.getMemberId().equals(memberId))
			.build();
	}
}
