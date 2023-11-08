package com.eoe.osori.domain.auth.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.eoe.osori.domain.BaseTimeEntity;
import com.eoe.osori.domain.auth.dto.PostAuthRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nickname;

	@Column
	private String profileImageUrl;

	@NotNull
	@Column
	private String provider;

	@NotNull
	@Column
	private String providerId;

	public static Member from(PostAuthRequestDto postAuthRequestDto){
		return Member.builder()
			.provider(postAuthRequestDto.getProvider())
			.providerId(postAuthRequestDto.getProviderId())
			.build();
	}
}
