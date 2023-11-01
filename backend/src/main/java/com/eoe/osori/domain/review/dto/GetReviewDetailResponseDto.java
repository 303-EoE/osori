package com.eoe.osori.domain.review.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.eoe.osori.domain.review.domain.Review;
import com.eoe.osori.domain.review.domain.ReviewImage;

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
		GetStoreResponseDto getStoreResponseDto, GetMemberResponseDto getMemberResponseDto) {
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
			.storeId(getStoreResponseDto.getStoreId())
			.storeName(getStoreResponseDto.getStoreName())
			.storeDepth1(getStoreResponseDto.getStoreDepth1())
			.storeDepth2(getStoreResponseDto.getStoreDepth2())
			.memberId(getMemberResponseDto.getMemberId())
			.memberNickname(getMemberResponseDto.getMemberNickname())
			.memberProfileImageUrl(getMemberResponseDto.getMemberProfileImageUrl())
			.build();
	}
}
