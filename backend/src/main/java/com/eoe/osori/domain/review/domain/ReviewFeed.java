package com.eoe.osori.domain.review.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collation = "review_feed")
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

	@Field
	private Boolean liked;

	@Field
	private Boolean isMine;


}
