package com.eoe.osori.domain.chat.domain.redis;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "redis_chat_room")
@Builder
public class RedisChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Indexed
    private Long chatRoomId;

    @Indexed
    private String nickname;

    public static RedisChatRoom of(Long chatRoomId, String nickname) {
        return RedisChatRoom.builder()
                .chatRoomId(chatRoomId)
                .nickname(nickname)
                .build();
    }

}
