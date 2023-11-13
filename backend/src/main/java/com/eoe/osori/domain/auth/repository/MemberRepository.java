package com.eoe.osori.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoe.osori.domain.auth.domain.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByProviderId(String providerId);
	Optional<Member> findByNickname(String nickname);
}
