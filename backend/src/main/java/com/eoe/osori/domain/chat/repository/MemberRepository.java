package com.eoe.osori.domain.chat.repository;


import com.eoe.osori.domain.chat.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsBymemberName(String memberName);
}
