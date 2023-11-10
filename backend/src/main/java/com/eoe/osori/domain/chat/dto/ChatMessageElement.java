package com.eoe.osori.domain.chat.dto;

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
public class ChatMessageElement {

    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private String senderNickname;
    private String senderProfileImageUrl;
    private String content;
    private int readCount;
    private LocalDateTime sendAt;
    private boolean isMine;

    public static ChatMessageElement of(ChatMessage chatMessage, Long memberId) {
        return ChatMessageElement.builder()
                .id(chatMessage.getId())
                .chatRoomId(chatMessage.getChatRoomId())
                .senderId(chatMessage.getSenderId())
                .senderNickname(chatMessage.getSenderNickname())
                .senderProfileImageUrl(chatMessage.getSenderProfileImageUrl())
                .content(chatMessage.getContent())
                .readCount(chatMessage.getReadCount())
                .sendAt(chatMessage.getCreatedAt())
                .isMine(chatMessage.getSenderId().equals(memberId))
                .build();
    }
}
