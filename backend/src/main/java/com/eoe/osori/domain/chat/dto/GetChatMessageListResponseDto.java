package com.eoe.osori.domain.chat.dto;

import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class GetChatMessageListResponseDto {

    private List<ChatMessageElement> chatMessages;

    public static GetChatMessageListResponseDto of(List<ChatMessage> chatMessageList, Long memberId) {
        return GetChatMessageListResponseDto.builder()
                .chatMessages(chatMessageList.stream()
                        .map(chatMessage -> ChatMessageElement.of(chatMessage, memberId))
                        .collect(Collectors.toList()))
                .build();
    }
}
