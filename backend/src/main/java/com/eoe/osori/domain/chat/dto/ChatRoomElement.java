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

    private Long chatRoomId;

    private Long joinedMemberId;

    private String nickname;

    private String profileImageUrl;

    private int unReadCount;

    private String content;

    private LocalDateTime sendAt;
    public static ChatRoomElement of(ChatRoom chatRoom, Long memberId, int unReadCount, ChatMessage chatMessage) {
        return ChatRoomElement.builder()
                .chatRoomId(chatRoom.getId())
                .joinedMemberId(chatRoom.getJoinMemberId() == memberId ? chatRoom.getCreateMemberId() : chatRoom.getJoinMemberId())
                .nickname(chatRoom.getJoinMemberId() == memberId ? chatRoom.getCreateMemberNickname() : chatRoom.getJoinMemberNickname())
                .profileImageUrl(chatRoom.getJoinMemberId() == memberId ? chatRoom.getCreateMemberProfileImageUrl() : chatRoom.getJoinMemberProfileImageUrl())
                .unReadCount(unReadCount)
                .content(chatMessage.getContent())
//                .sendAt(chatMessage.getCreatedAt())
                .sendAt(LocalDateTime.now())
                .build();
    }
}
