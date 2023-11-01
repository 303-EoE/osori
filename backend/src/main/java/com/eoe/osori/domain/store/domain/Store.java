package com.eoe.osori.domain.store.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.eoe.osori.domain.BaseTimeEntity;
import com.eoe.osori.domain.store.dto.PostStoreRequestDto;
import com.eoe.osori.global.common.api.kakao.dto.GetDistrictResponseDto;
import com.eoe.osori.global.meta.converter.StoreCategoryConverter;
import com.eoe.osori.global.meta.domain.StoreCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

@Entity(name = "store")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE store SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Store extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@NotNull
	@Column
	private String name;

	@Size(max = 20)
	@NotNull
	@Column
	private String kakaoId;

	@NotNull
	@Column(length = 10)
	@Convert(converter = StoreCategoryConverter.class)
	private StoreCategory category;

	@Size(max = 30)
	@NotNull
	@Column
	private String longitude;

	@Size(max = 30)
	@NotNull
	@Column
	private String latitude;

	@Size(max = 20)
	@NotNull
	@Column
	private String depth1;

	@Size(max = 20)
	@NotNull
	@Column
	private String depth2;

	@Size(max = 100)
	@NotNull
	@Column
	private String roadAddressName;

	@Size(max = 100)
	@NotNull
	@Column
	private String addressName;

	@Size(max = 20)
	@NotNull
	@Column
	private String phone;

	@NotNull
	@Builder.Default
	@ColumnDefault("false")
	@Column
	private Boolean deleted = Boolean.FALSE;

	public static Store of(PostStoreRequestDto postStoreRequestDto, GetDistrictResponseDto getDistrictResponseDto) {
		return Store.builder()
			.name(postStoreRequestDto.getName())
			.kakaoId(postStoreRequestDto.getKakaoId())
			.category(StoreCategory.ofKakaoName(postStoreRequestDto.getCategory()))
			.longitude(postStoreRequestDto.getLongitude())
			.latitude(postStoreRequestDto.getLatitude())
			.depth1(getDistrictResponseDto.getDepth1())
			.depth2(getDistrictResponseDto.getDepth2())
			.roadAddressName(postStoreRequestDto.getRoadAddressName())
			.addressName(postStoreRequestDto.getAddressName())
			.phone(postStoreRequestDto.getPhone())
			.build();
	}
}
