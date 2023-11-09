package com.eoe.osori.global.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eoe.osori.domain.auth.domain.Member;
import com.eoe.osori.domain.auth.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.MemberException;
import com.eoe.osori.global.advice.error.info.MemberErrorInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findById(Long.parseLong(username))
			.orElseThrow(() -> new MemberException(MemberErrorInfo.MEMBER_NOT_FOUND));

		return UserDetailsImpl.builder()
			.id(member.getId())
			.build();
	}
}
