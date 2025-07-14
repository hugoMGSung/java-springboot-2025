package com.pknu.backboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.MemberRepository;
import com.pknu.backboard.security.MemberRole;

import lombok.RequiredArgsConstructor;

// 스프링 시큐리티 로그인용 서비스
@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;

    // UserDetailsService 인터페이스 내에 정의해놓은 메서드 구현
    // 스프링시큐리티와 직접만든 회원정보 테이블은 매핑하는 작업 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> opMember = this.memberRepository.findByEmail(username);
        if (opMember.isEmpty()) {   // username을 가진 회원이 없으면
            throw new UsernameNotFoundException(String.format("%s 사용자가 없습니다", username));
        }

        Member member = opMember.get(); 

        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (member.getRole().equals(MemberRole.ADMIN)) {        
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue())); // ROLE_ADMIN
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue())); // ROLE_USER
        }

        // Member 를 직접쓰는게 아니고, 스프링 시큐리티의 User라는 객체로 로그인, 로그아웃을 대체
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
