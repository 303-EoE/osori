package com.eoe.osori.global.common.api.member.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter // 나중에 삭제 예정
public class GetMemberDetailResponseDto {

    private Long id;
    private String nickname;
    private String profileImageUrl;

}
