package com.eoe.osori.domain.chat.controller;


import com.eoe.osori.domain.chat.dto.ChatMessageRequestDto;
import com.eoe.osori.domain.chat.dto.GetChatMessageListResponseDto;
import com.eoe.osori.domain.chat.dto.GetChatRoomListResponseDto;
import com.eoe.osori.domain.chat.service.ChatRoomSevice;
import com.eoe.osori.domain.chat.service.redis.RedisChatRoomService;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatRoomSevice chatRoomSevice;
    private final RedisChatRoomService redisChatRoomService;


    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    /**
     * 전체 채팅방 가져오기
     *
     * @return
     */
    @GetMapping("/rooms")
//    public ResponseEntity<EnvelopeResponse<GetChatRoomListResponseDto>> getChatRoomListByMemberId(@RequestHeader("Authorization") String accesToken) {
    public ResponseEntity<EnvelopeResponse<GetChatRoomListResponseDto>> getChatRoomListByMemberId(@RequestParam Long memberId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<GetChatRoomListResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(chatRoomSevice.getChatRoomListByMemberId(memberId))
                        .build());
    }

    /**
     * 채팅방 만들기
     *
     * @param memberId
     * @return
     */
    @PostMapping("/room")
//    public ChatRoom createRoom(@RequestHeader("Authorization") String accesToken, @RequestParam Long memberId) {
    public ResponseEntity<EnvelopeResponse<CommonIdResponseDto>> createRoom(@RequestParam Long memberId) {
        // 헤더에서 내 memberId 추출해서 해당 채팅방 존재하는지 확인하기!!
        Long createMemberId = memberId;
        Long joinMemberId = 10L;
        System.out.println("채팅방 만들기");
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<CommonIdResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(chatRoomSevice.checkChatRoom(createMemberId, joinMemberId))
                        .build());
    }

    /**
     * 채팅방 입장 / 채팅 내역 조회
     *
     * @param chatRoomId
     * @return
     */
    @GetMapping("/room/enter")
    public ResponseEntity<EnvelopeResponse<GetChatMessageListResponseDto>> enterChatRoom(@RequestParam Long chatRoomId) {
        // 헤더에서 memberId 받아오기
        Long memberId = 10L;
        return ResponseEntity.status(HttpStatus.OK)
                .body(EnvelopeResponse.<GetChatMessageListResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(chatRoomSevice.getChatMessageListByChatRoomId(chatRoomId, memberId))
                        .build());
    }

    /**
     * 첫번째 로직
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     *
     * @param chatMessageRequestDto ChatMessageRequestDto
     */
    @MessageMapping("/message")
    public void message(ChatMessageRequestDto chatMessageRequestDto) {
        // template를 설정해 놓았으므로, 토픽이름과 메세지만 담아서 보내면 된다.
        Long memberId = 10L;

        chatRoomSevice.sendMessage(chatMessageRequestDto, memberId);
    }

    @PostMapping("/room/out")
    public void disconnectChat(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("nickname") String nickname) {
        redisChatRoomService.disconnectChatRoom(chatRoomId, nickname);
    }

    // 메시지 전송 후 callback
//    @PostMapping("/room/notification")
//    public ResponseEntity<Message> sendNotification(@RequestBody ChatMessage chatMessage) {
//        Message savedMessage = chatService.sendNotificationAndSaveMessage(message);
//        return ResponseEntity.ok(savedMessage);
//    }
}
