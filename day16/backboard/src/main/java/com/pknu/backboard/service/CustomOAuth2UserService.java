package com.pknu.backboard.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.pknu.backboard.dto.GoogleResponse;
import com.pknu.backboard.dto.NaverResponse;
import com.pknu.backboard.dto.OAuth2Reponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

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

        return null;
    }
}
