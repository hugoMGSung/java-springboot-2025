package com.pknu.backboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.pknu.backboard.dto.CustomOAuth2User;
import com.pknu.backboard.dto.GoogleResponse;
import com.pknu.backboard.dto.MemberDto;
import com.pknu.backboard.dto.OAuth2Response;
import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @SuppressWarnings("unused")
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            return null;
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }
        
        String username = oAuth2Response.getEmail();  // 이메일로 모두 처리

        Optional<Member> opMember = memberRepository.findByEmail(username);

        if (opMember.isEmpty()) {  // 한번도 들어오지 않은 사람
            Member member = new Member();
            member.setUsername(username);
            member.setEmail(username);
            member.setRole("ROLE_USER");
            member.setRegDate(LocalDateTime.now());

            memberRepository.save(member);

            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(username);
            memberDto.setEmail(username);
            memberDto.setName(username);
            memberDto.setRole("ROLE_USER");

            return new CustomOAuth2User(memberDto);
        } else {
            Member existMember = opMember.get();
            existMember.setUsername(username);
            existMember.setEmail(username);
            existMember.setRole("ROLE_USER");

            memberRepository.save(existMember);

            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(existMember.getUsername());
            memberDto.setEmail(existMember.getEmail());
            memberDto.setName(existMember.getUsername());
            memberDto.setRole("ROLE_USER");

            return new CustomOAuth2User(memberDto);
        }
    }
}
