package com.eoe.osori.domain.chat.service;

import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import com.eoe.osori.domain.chat.dto.ChatMessageRequestDto;
import com.eoe.osori.global.advice.error.exception.ChatException;
import com.eoe.osori.global.advice.error.info.ChatErrorInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 세번째 로직
     * Kafka에서 메시지가 발행(publish)되면 대기하고 있던 Kafka Consumer가 해당 메시지를 받아 처리한다.
     */
    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void sendMessage(ChatMessage chatMessage) {
        try {
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoomId(), chatMessage); // Websocket 구독자에게 채팅 메시지 Send
        } catch (Exception e) {
            throw new ChatException(ChatErrorInfo.FAIL_TO_SEND_MESSAGE);
        }
    }
}