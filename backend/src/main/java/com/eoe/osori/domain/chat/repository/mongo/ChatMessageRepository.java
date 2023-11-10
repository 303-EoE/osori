package com.eoe.osori.domain.chat.repository.mongo;

import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,Long> {

    @Query("{ 'chatRoomId' : ?0, 'senderId' : ?1, 'readCount' : 1 }")
    int countByChatRoomIdAndSenderIdAndReadCount(Long chatRoomId, Long senderId);

    ChatMessage findFirstByChatRoomNoOrderByCreatedAtDesc(Long chatRoomId);

    List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
