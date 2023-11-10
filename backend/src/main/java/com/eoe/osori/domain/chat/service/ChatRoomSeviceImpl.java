package com.eoe.osori.domain.chat.service;


import com.eoe.osori.domain.chat.domain.ChatRoom;
import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import com.eoe.osori.domain.chat.dto.ChatRoomElement;
import com.eoe.osori.domain.chat.dto.GetChatMessageListResponseDto;
import com.eoe.osori.domain.chat.dto.GetChatRoomListResponseDto;
import com.eoe.osori.domain.chat.repository.ChatRoomRepository;
import com.eoe.osori.domain.chat.repository.mongo.ChatMessageRepository;
import com.eoe.osori.global.common.api.member.MemberApi;
import com.eoe.osori.global.common.api.member.dto.GetMemberDetailResponseDto;
import com.eoe.osori.global.common.response.CommonIdResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomSeviceImpl implements ChatRoomSevice {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberApi memberApi;

    @Override
    public CommonIdResponseDto checkChatRoom(Long createMemberId, Long joinMemberId) {

        // 방이 없으면 만들고 있으면 찾아준다.
        ChatRoom chatRoom = chatRoomRepository.findByCreateMemberIdAndJoinMemberId(createMemberId, joinMemberId)
                .orElse(createChatRoom(createMemberId, joinMemberId));

        return CommonIdResponseDto.from(chatRoom.getId());
    }

    @Transactional
    public ChatRoom createChatRoom(Long createMemberId, Long joinMemberId) {
        EnvelopeResponse<GetMemberDetailResponseDto> getMemberDetailResponseDtoEnvelopeResponse;

//        try {
//            getMemberDetailResponseDtoEnvelopeResponse = memberApi.getMemberDetail(createMemberId);
//        } catch (FeignException e) {
//            throw new ChatException(ChatErrorInfo.FAIL_TO_FEIGN_CLIENT_REQUEST);
//        }
//        GetMemberDetailResponseDto createMember = getMemberDetailResponseDtoEnvelopeResponse.getData();
        GetMemberDetailResponseDto createMember = new GetMemberDetailResponseDto();

//        try {
//            getMemberDetailResponseDtoEnvelopeResponse = memberApi.getMemberDetail(joinMemberId);
//        } catch (FeignException e) {
//            throw new ChatException(ChatErrorInfo.FAIL_TO_FEIGN_CLIENT_REQUEST);
//        }
//        GetMemberDetailResponseDto joinMember = getMemberDetailResponseDtoEnvelopeResponse.getData();
        GetMemberDetailResponseDto joinMember = new GetMemberDetailResponseDto();

        createMember.setId(createMemberId);
        createMember.setNickname("현수");
        createMember.setProfileImageUrl("aaaaaaaaaaa");
        joinMember.setId(joinMemberId);
        joinMember.setNickname("준호");
        joinMember.setProfileImageUrl("bbbbbbbb");

        ChatRoom chatRoom = ChatRoom.of(createMember, joinMember);
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    @Override
    public GetChatRoomListResponseDto getChatRoomListByMemberId(Long memberId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByMemberId(memberId);

        // 채팅방이 없으면 빈 리스트를 보낸다
        if (chatRoomList.size() == 0) {
            return new GetChatRoomListResponseDto(new ArrayList<>());
        }

        // 채팅방이 있으면 가져온다.
        List<ChatRoomElement> chatRooms = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            int unReadCount = chatMessageRepository.countByChatRoomIdAndSenderIdAndReadCount(chatRoom.getId(), chatRoom.getJoinMemberId() == memberId ? chatRoom.getCreateMemberId() : chatRoom.getJoinMemberId());
            ChatMessage chatMessage = chatMessageRepository.findFirstByChatRoomNoOrderByCreatedAtDesc(chatRoom.getId());
            chatRooms.add(ChatRoomElement.of(chatRoom, memberId, unReadCount, chatMessage));
        }

        return new GetChatRoomListResponseDto(chatRooms);
    }

    @Override
    public GetChatMessageListResponseDto getChatMessageListByChatRoomId(Long chatRoomId, Long memberId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoomId(chatRoomId);

        // 채팅 메세지가 없다면 빈 리스트를 보낸다.
        if (chatMessageList.size() == 0) {
            return new GetChatMessageListResponseDto(new ArrayList<>());
        }

        return GetChatMessageListResponseDto.of(chatMessageList,memberId);
    }




//    @Transactional
//    public void connectChatRoom(Integer chatRoomNo, String email) {
//        ChatRoom chatRoom = ChatRoom.builder()
//                        .email(email)
//                        .chatroomNo(chatRoomNo)
//                        .build();
//
//        chatRoomRepository.save(chatRoom);
//    }
//
//    @Transactional
//    public void disconnectChatRoom(Integer chatRoomNo, String email) {
//        ChatRoom chatRoom = chatRoomRepository.findByChatroomNoAndEmail(chatRoomNo, email)
//                        .orElseThrow(IllegalStateException::new);
//
//        chatRoomRepository.delete(chatRoom);
//    }
//
//    public boolean isAllConnected(Integer chatRoomNo) {
//        List<ChatRoom> connectedList = chatRoomRepository.findByChatroomNo(chatRoomNo);
//        return connectedList.size() == 2;
//    }
//
//    public boolean isConnected(Integer chatRoomNo) {
//        List<ChatRoom> connectedList = chatRoomRepository.findByChatroomNo(chatRoomNo);
//        return connectedList.size() == 1;
//    }
}
