package com.eoe.osori.domain.chat.controller;


import com.eoe.osori.domain.chat.domain.ChatRoom;
import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import com.eoe.osori.domain.chat.dto.GetChatMessageListResponseDto;
import com.eoe.osori.domain.chat.dto.GetChatRoomListResponseDto;
import com.eoe.osori.domain.chat.service.ChatRoomSevice;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatRoomSevice chatRoomSevice;
    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private final NewTopic topic;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
//    public ResponseEntity<EnvelopeResponse<GetChatRoomListResponseDto>> getChatRoomListByMemberId(@RequestHeader("Authorization") String accesToken) {
    public ResponseEntity<EnvelopeResponse<GetChatRoomListResponseDto>> getChatRoomListByMemberId() {

        // 헤더에서 id 값 가져오는 로직 짜야함!!
        Long memberId = 10L;

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<GetChatRoomListResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(chatRoomSevice.getChatRoomListByMemberId(memberId))
                        .build());
    }

    @PostMapping("/room")
    @ResponseBody
//    public ChatRoom createRoom(@RequestHeader("Authorization") String accesToken, @RequestParam Long memberId) {
    public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> createRoom(@RequestParam Long memberId) {
        // 헤더에서 내 memberId 추출해서 해당 채팅방 존재하는지 확인하기!!
        Long createMemberId = memberId;
        Long joinMemberId = 10L;

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<CommonIdResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(chatRoomSevice.checkChatRoom(createMemberId, joinMemberId))
                        .build());
    }

    @GetMapping("/room/{chatRoomId}")
    public  ResponseEntity<EnvelopeResponse<GetChatMessageListResponseDto>> enterChatRoom(@PathVariable Long chatRoomId) {
        // 헤더에서 memberId 받아오기
        Long memberId=10L;
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<GetChatMessageListResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(chatRoomSevice.getChatMessageListByChatRoomId(chatRoomId, memberId))
                        .build());
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable Long id) {
        return chatRoomSeviceImpl.getChatRoom(id);
    }

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
