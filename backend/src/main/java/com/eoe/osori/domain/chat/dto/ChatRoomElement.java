package com.eoe.osori.domain.chat.dto;

import com.eoe.osori.domain.chat.domain.ChatRoom;
import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatRoomElement {

    private Long id;

    private Long memberId;

    private String nickname;

    private String profileImageUrl;

    private int unReadCount;

    private String content;

    private LocalDateTime sendAt;
    public static ChatRoomElement of(ChatRoom chatRoom, Long memberId, int unReadCount, ChatMessage chatMessage) {
        return ChatRoomElement.builder()
                .id(chatRoom.getId())
                .memberId(chatRoom.getJoinMemberId() == memberId ? chatRoom.getJoinMemberId() : chatRoom.getCreateMemberId())
                .nickname(chatRoom.getJoinMemberId() == memberId ? chatRoom.getJoinMemberNickname() : chatRoom.getCreateMemberNickname())
                .profileImageUrl(chatRoom.getJoinMemberId() == memberId ? chatRoom.getJoinMemberProfileImageUrl() : chatRoom.getCreateMemberProfileImageUrl())
                .unReadCount(unReadCount)
                .content(chatMessage.getContent())
                .sendAt(chatMessage.getCreatedAt())
                .build();
    }
}
