package com.eoe.osori.domain.store.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.eoe.osori.domain.BaseTimeEntity;
import com.eoe.osori.global.meta.domain.StoreCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String name;
	private String kakaoId;
	private StoreCategory category;
	private String longitude;
	private String latitude;
	private String depth1;
	private String depth2;
	private String roadAddressName;
	private String addressName;
	private String phone;
	private Boolean deleted = Boolean.FALSE;
}
