package com.eoe.osori.domain.chat.service.redis;

public interface RedisChatRoomService {

    void connectChatRoom(Long chatRoomId, String nickname);

    void disconnectChatRoom(Long chatRoomId, String nickname);

    boolean isAllConnected(Long chatRoomId);

    boolean isConnected(Long chatRoomId);
}
