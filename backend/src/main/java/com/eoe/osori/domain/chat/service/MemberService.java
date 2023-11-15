package com.eoe.osori.domain.chat.service;


import com.eoe.osori.domain.chat.dto.Member;
import com.eoe.osori.domain.chat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseEntity<?> createMember(Member member) {
        // 빈칸이 존재하는지 확인
        if (member.getMemberId().equals("") || member.getMemberPwd().equals("") || member.getMemberName().equals("")) {
            return new ResponseEntity<>("빈칸 존재", HttpStatus.BAD_REQUEST);
        }
        // memberId가 이미 존재하는지 확인
        if (memberRepository.existsById(member.getMemberId())) {
            return new ResponseEntity<>("이미 존재하는 아이디", HttpStatus.BAD_REQUEST);
        }
        // memberName이 이미 존재하는지 확인
        if (memberRepository.existsBymemberName(member.getMemberName())) {
            return new ResponseEntity<>("이미 존재하는 닉네임", HttpStatus.BAD_REQUEST);
        }
        // 사용자의 비밀번호를 해싱하여 저장
        String hashedPassword = BCrypt.hashpw(member.getMemberPwd(), BCrypt.gensalt());
        member.setMemberPwd(hashedPassword);
        member.setMemberImage("https://osori-bucket.s3.ap-northeast-2.amazonaws.com/2023/11/13/fd6206ea-031f-4489-80e1-7f535ef5854b.png");
        Member savedMember = memberRepository.save(member);
        return new ResponseEntity<>(savedMember, HttpStatus.OK);
    }

    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    public ResponseEntity<Member> getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    public ResponseEntity<?> login(Long id, Map<String, String> passwordData) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 비밀번호 확인
            if (BCrypt.checkpw(passwordData.get("memberPwd"), member.getMemberPwd())) {
                return new ResponseEntity<>(member, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
