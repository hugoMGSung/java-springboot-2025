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
import com.pknu.backboard.dto.NaverResponse;
import com.pknu.backboard.dto.OAuth2Reponse;
import com.pknu.backboard.entity.Member;
import com.pknu.backboard.repository.MemberRepository;
import com.pknu.backboard.security.MemberRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());  // JSON 형태 데이터 출력

        String registrationId = userRequest.getClientRegistration().getRegistrationId();  // PROVIDER naver/google/kakao
        OAuth2Reponse oAuth2Reponse = null; 

        if (registrationId.equals("naver")) {
            oAuth2Reponse = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Reponse = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 회원가입과 동일한 작업
        String email = oAuth2Reponse.getEmail();  

        Optional<Member> opMember = memberRepository.findByEmail(email); // MemberTable에 같은 계정이 있는지 확인
        MemberDto memberDto = new MemberDto();
        
        if (opMember.isEmpty()) {  // 한번도 들어오지 않은 소셜 사용자
            Member member = new Member();
            member.setEmail(email);
            member.setUsername(email);
            member.setRole(MemberRole.USER);  
            member.setProvider(registrationId);
            member.setRegDate(LocalDateTime.now());

            memberRepository.save(member);

            // 로그인 세션에 넣을 정보
            memberDto.setUsername(email);
            memberDto.setEmail(email);
            memberDto.setProvider(registrationId);
            memberDto.setRole(MemberRole.USER);

        } else {  // 존재하면 이전 값을 업데이트
            Member existMember = opMember.get();
            existMember.setEmail(email);
            existMember.setUsername(email);
            existMember.setRole(MemberRole.USER);
            existMember.setProvider(registrationId);

            memberRepository.save(existMember);

            // 있는 정보 넣어주기
            memberDto.setUsername(existMember.getUsername());
            memberDto.setEmail(existMember.getEmail());
            memberDto.setProvider(existMember.getProvider());
            memberDto.setRole(existMember.getRole());
        }

        return new CustomOAuth2User(memberDto);
    }
}
