package com.pknu.backboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Member setMember(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        //member.setPassword(password);  // 12345 가 그대로 들어감
        // BCryptPasswordEncoder pwdEnc = new BCryptPasswordEncoder();
        member.setRegDate(LocalDateTime.now());  // 날짜저장 추가
        // SecurityConfig에 새로 선언한 passwordEncoder로 암호화
        member.setPassword(passwordEncoder.encode(password));  // 12345 -> EFWEFER@#$$@#$FEFG#@324423WR 와 같이 암호화

        this.memberRepository.save(member);
        return member;
    }

    // 같은 이름의 사용자 존재여부 파악 메서드
    public boolean getExistMember(String username) {
        Optional<Member> opMember = this.memberRepository.findByUsername(username);
        if (opMember.isPresent()) {
            return true;  // 같은 아이디 사용자 존재
        } else {
            return false; // 없음. 회원가입 가능
        }
    }

    // 실제 사용자 객체 가져오기
    public Member getMember(String username) {
        Optional<Member> opMember = this.memberRepository.findByUsername(username);
        if (opMember.isPresent()) {
            return opMember.get();   // Member 인스턴스 리턴
        } else {
            throw new RuntimeException("member not found");
        }
    }
}
