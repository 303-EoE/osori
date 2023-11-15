package com.eoe.osori.domain.chat.service.redis;

import com.eoe.osori.domain.chat.domain.redis.RedisChatRoom;
import com.eoe.osori.domain.chat.repository.redis.RedisChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisChatRoomServiceImpl implements RedisChatRoomService {

    private final RedisChatRoomRepository redisChatRoomRepository;

    @Override
    public void connectChatRoom(Long chatRoomId, String nickname) {
        redisChatRoomRepository.save(RedisChatRoom.of(chatRoomId, nickname));
    }

    @Override
    public void disconnectChatRoom(Long chatRoomId, String nickname) {
        // 예외처리를 해줘야하나?? 삭제가 이미 되어있다면 예외처리는 필요없는거아닌가?
        RedisChatRoom redisChatRoom = redisChatRoomRepository.findByChatRoomIdAndNickname(chatRoomId, nickname)
                .orElse(null);
        if (redisChatRoom != null) {
            redisChatRoomRepository.delete(redisChatRoom);
        }
        log.info("채팅방에서 나가졌습니다.");
    }

    @Override
    public boolean isAllConnected(Long chatRoomId) {
        List<RedisChatRoom> redisChatRoomList = redisChatRoomRepository.findByChatRoomId(chatRoomId);
        return redisChatRoomList.size() == 2;
    }

    @Override
    public boolean isConnected(Long chatRoomId) {
        List<RedisChatRoom> redisChatRoomList = redisChatRoomRepository.findByChatRoomId(chatRoomId);
        return redisChatRoomList.size() == 1;
    }
}
