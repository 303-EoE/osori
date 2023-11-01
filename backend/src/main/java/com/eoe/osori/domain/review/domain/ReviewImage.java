package com.eoe.osori.domain.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "review_image")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 255)
	@Column
	private String url;

	@NotNull
	@Column
	private Long reviewId;

	public static ReviewImage of(Long reviewId, String url) {
		return ReviewImage.builder()
			.url(url)
			.reviewId(reviewId)
			.build();
	}
}
