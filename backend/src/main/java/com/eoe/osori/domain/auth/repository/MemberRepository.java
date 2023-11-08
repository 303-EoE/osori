package com.eoe.osori.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoe.osori.domain.auth.domain.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByProviderId(String providerId);
}
