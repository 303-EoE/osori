package com.eoe.osori.domain.chat.service;

import com.eoe.osori.domain.chat.dto.GetChatMessageListResponseDto;
import com.eoe.osori.domain.chat.dto.GetChatRoomListResponseDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;

public interface ChatRoomSevice {
    GetChatRoomListResponseDto getChatRoomListByMemberId(Long memberId);

    CommonIdResponseDto checkChatRoom(Long createMemberId, Long joinMemberId);

    GetChatMessageListResponseDto getChatMessageListByChatRoomId(Long chatRoomId, Long memberId);
}
