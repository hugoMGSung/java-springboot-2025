package com.pknu.backboard.dto;

public interface OAuth2Reponse {

    String getProvider();  // 로그인 제공자 naver, google, kakao
    String getProviderId();  // 제공자 발급 아이디
    String getEmail();      // 가입한 이메일
    String getName();       // 가입한 이름(프로필)
}