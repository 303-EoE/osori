package com.eoe.osori.domain.review.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.eoe.osori.global.common.api.store.dto.GetMemberResponseDto;
import com.eoe.osori.global.common.api.store.dto.GetStoreDetailResponseDto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "review_feed")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFeed {

	@Id
	private String id;

	@Field
	private LocalDateTime createdAt;

	@Field
	private Integer averageCost;

	@Field
	private String content;

	@Field
	private Double rate;

	@Field
	private String billType;

	@Field
	private Long storeId;

	@Field
	private String storeName;

	@Field
	private String storeDepth1;

	@Field
	private String storeDepth2;

	@Field
	private Long memberId;

	@Field
	private String memberNickname;

	@Field
	private String memberProfileImageUrl;

	@Field
	@Builder.Default
	private List<String> images = new ArrayList<>();

	public static ReviewFeed of(Review review, GetMemberResponseDto getMemberResponseDto,
		GetStoreDetailResponseDto getStoreResponseDto, List<String> reviewImageUrlList) {

		return ReviewFeed.builder()
			.id(Long.toString(review.getId()))
			.createdAt(review.getCreatedAt())
			.averageCost(review.getAverageCost())
			.content(review.getContent())
			.rate(review.getRate())
			.billType(review.getBillType().getName())
			.storeId(getStoreResponseDto.getId())
			.storeName(getStoreResponseDto.getName())
			.storeDepth1(getStoreResponseDto.getDepth1())
			.storeDepth2(getStoreResponseDto.getDepth2())
			.memberId(getMemberResponseDto.getId())
			.memberNickname(getMemberResponseDto.getNickname())
			.memberProfileImageUrl(getMemberResponseDto.getProfileImageUrl())
			.images(reviewImageUrlList)
			.build();
	}
}
