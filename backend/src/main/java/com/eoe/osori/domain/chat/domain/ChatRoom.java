package com.eoe.osori.domain.chat.domain;

import com.eoe.osori.domain.BaseTimeEntity;
import com.eoe.osori.global.common.api.member.dto.GetMemberDetailResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@Table(name="chat_room")
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Long createMemberId;

    @NotNull
    @Column
    private String createMemberNickname;

    @NotNull
    @Column
    private String createMemberProfileImageUrl;

    @NotNull
    @Column
    private Long joinMemberId;

    @NotNull
    @Column
    private String joinMemberNickname;

    @NotNull
    @Column
    private String joinMemberProfileImageUrl;

    public static ChatRoom of(GetMemberDetailResponseDto createMember,GetMemberDetailResponseDto joinMember) {
        return ChatRoom.builder()
                .createMemberId(createMember.getId())
                .createMemberNickname(createMember.getNickname())
                .createMemberProfileImageUrl(createMember.getProfileImageUrl())
                .joinMemberId(joinMember.getId())
                .joinMemberNickname(joinMember.getNickname())
                .joinMemberProfileImageUrl(joinMember.getProfileImageUrl())
                .build();
    }
}
