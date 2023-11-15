package com.eoe.osori.domain.chat.repository.redis;

import com.eoe.osori.domain.chat.domain.ChatRoom;
import com.eoe.osori.domain.chat.domain.redis.RedisChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RedisChatRoomRepository extends CrudRepository<RedisChatRoom, Long> {
    List<RedisChatRoom> findByChatRoomId(Long chatRoomId);

    Optional<RedisChatRoom> findByChatRoomIdAndNickname(Long chatRoomId, String nickname);
}
