package com.eoe.osori.domain.chat.controller;

import com.june.chatdemo.domain.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {
    // kafkaConfig에서 설정해 놓은 template 설정을 사용한다.
    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private final NewTopic topic;

    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage) {
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님 등장!");
        }
        System.out.println(topic.name());
        // template를 설정해 놓았으므로, 토픽이름과 메세지만 담아서 보내면 된다.
        // 어디로 보내는거지??
        kafkaTemplate.send(topic.name(), chatMessage);
    }
}
