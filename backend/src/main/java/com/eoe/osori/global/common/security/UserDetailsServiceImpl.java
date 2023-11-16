package com.eoe.osori.global.common.security;

import com.eoe.osori.domain.auth.domain.Member;
import com.eoe.osori.domain.auth.repository.MemberRepository;
import com.eoe.osori.global.advice.error.exception.AuthException;
import com.eoe.osori.global.advice.error.info.AuthErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(username))
                .orElseThrow(() -> new AuthException(AuthErrorInfo.MEMBER_NOT_FOUND));

        return UserDetailsImpl.builder()
                .id(member.getId())
                .build();
    }
}
