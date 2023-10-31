package com.eoe.osori.domain.review.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

import com.eoe.osori.domain.BaseTimeEntity;
import com.eoe.osori.domain.review.dto.PostReviewRequestDto;
import com.eoe.osori.global.meta.converter.BillTypeConverter;
import com.eoe.osori.global.meta.domain.BillType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "review")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE review SET deleted = true where id = ?")
public class Review extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, nullable = false)
	private LocalDateTime paidAt;

	@NotNull
	@Column
	private Integer totalPrice;

	@NotNull
	@Column
	private Integer headcount;

	@NotNull
	@Column
	private Integer factor;

	@NotNull
	@Column
	private Integer averageCost;

	@NotNull
	@Column(length = 255)
	private String content;

	@NotNull
	@Column
	private Double rate;

	@NotNull
	@Column
	private Long memberId;

	@NotNull
	@Column
	private Long storeId;

	@NotNull
	@Builder.Default
	@ColumnDefault("false")
	@Column
	private Boolean deleted = Boolean.FALSE;

	@NotNull
	@Column(length = 20)
	@Convert(converter = BillTypeConverter.class)
	private BillType billType;

	// 이미지 넣는 컬럼 추가해줘야!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public static Review from(PostReviewRequestDto postReviewRequestDto) {
		return Review.builder()
			.paidAt(postReviewRequestDto.getPaidAt())
			.totalPrice(postReviewRequestDto.getTotalPrice())
			.headcount(postReviewRequestDto.getHeadcount())
			.factor(postReviewRequestDto.getFactor())
			.content(postReviewRequestDto.getContent())
			.rate(postReviewRequestDto.getRate())
			.storeId(postReviewRequestDto.getStoreId())
			.billType(BillType.ofName(postReviewRequestDto.getBillType()))
			.build();
	}

	
	/*
	지울거
	 */
	public void setMemberId(Long id) {
		this.memberId = id;
	}
	/*
	지워!!!!!!!!!!!!!!!!!!!!
	 */

	/**
	 * 
	 * 평균 가격 계산해주는 메서드
	 *
	 * @param totalPrice Integer
	 * @param factor Integer
	 * @param headcount Integer
	 */
	public void updateAverageCost(Integer totalPrice, Integer factor, Integer headcount) {

		this.averageCost = totalPrice / (factor * headcount);
	}
}
