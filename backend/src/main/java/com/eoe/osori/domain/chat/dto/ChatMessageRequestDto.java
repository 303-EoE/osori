package com.eoe.osori.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChatMessageRequestDto {
    private Long chatRoomId;
    private String content;
}
