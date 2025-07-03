package com.pknu.backboard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public Member setMember(String username, String email, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setEmail(email);
        //member.setPassword(password);  // 12345 가 그대로 들어감
        BCryptPasswordEncoder pwdEnc = new BCryptPasswordEncoder();
        member.setRegDate(LocalDateTime.now());  // 날짜저장 추가
        member.setPassword(pwdEnc.encode(password));  // 12345 -> EFWEFER@#$$@#$FEFG#@324423WR 와 같이 암호화

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
}
