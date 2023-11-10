package com.eoe.osori.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetChatRoomListResponseDto {
    List<ChatRoomElement> chatRooms;
}

