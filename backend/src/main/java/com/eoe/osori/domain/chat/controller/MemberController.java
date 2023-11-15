package com.eoe.osori.domain.chat.controller;


import com.eoe.osori.domain.chat.dto.Member;
import com.eoe.osori.domain.chat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody Member member) {
        System.out.println("회원가입에 오는곳");
        return memberService.createMember(member);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> login(@PathVariable Long id, @RequestBody Map<String, String> passwordData) {
        return memberService.login(id, passwordData);
    }
}