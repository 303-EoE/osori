package com.eoe.osori.domain.chat.repository;


import com.eoe.osori.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findById(Long id);

    @Query("SELECT c FROM ChatRoom c WHERE (c.createMemberId = :memberId OR c.joinMemberId = :memberId)")
    List<ChatRoom> findAllByMemberId(Long memberId);

    @Query("SELECT c FROM ChatRoom c WHERE (c.createMemberId = :createMemberId AND c.joinMemberId = :joinMemberId) OR (c.createMemberId = :joinMemberId AND c.joinMemberId = :createMemberId)")
    Optional<ChatRoom> findByCreateMemberIdAndJoinMemberId(@Param("createMemberId") Long createMemberId, @Param("joinMemberId") Long joinMemberId);
}
