package com.eoe.osori.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.eoe.osori.domain.review.domain.Review;
import com.eoe.osori.domain.review.domain.ReviewImage;
import com.eoe.osori.global.common.api.member.dto.GetMemberResponseDto;
import com.eoe.osori.global.common.api.store.dto.GetStoreDetailResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GetReviewDetailResponseDto {

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
	// liked, isMine 처리도 아직 남음!!!!!!!!!!!!!!!!!!
	private Boolean liked;
	private Boolean isMine;

	public static GetReviewDetailResponseDto of(Review review, List<ReviewImage> reviewImages,
		GetStoreDetailResponseDto getStoreResponseDto, GetMemberResponseDto getMemberResponseDto) {
		return GetReviewDetailResponseDto.builder()
			.id(review.getId())
			.createdAt(review.getCreatedAt())
			.averageCost(review.getAverageCost())
			.content(review.getContent())
			.rate(review.getRate())
			.billType(review.getBillType().getName())
			.images(reviewImages.stream()
				.map(reviewImage -> reviewImage.getUrl())
				.collect(Collectors.toList()))
			.storeId(getStoreResponseDto.getId())
			.storeName(getStoreResponseDto.getName())
			.storeDepth1(getStoreResponseDto.getDepth1())
			.storeDepth2(getStoreResponseDto.getDepth2())
			.memberId(getMemberResponseDto.getId())
			.memberNickname(getMemberResponseDto.getNickname())
			.memberProfileImageUrl(getMemberResponseDto.getProfileImageUrl())
			.build();
	}
}
