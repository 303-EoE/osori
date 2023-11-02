package com.eoe.osori.domain.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "like_review")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private Long reviewId;

	@NotNull
	@Column
	private Long memberId;

	public static LikeReview of(Long reviewId, Long memberId) {
		return LikeReview.builder()
			.reviewId(reviewId)
			.memberId(memberId)
			.build();
	}
}
