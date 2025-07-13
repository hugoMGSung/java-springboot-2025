package com.pknu.backboard.dto;

public interface OAuth2Response {

    String getProvider();  // 로그인 제공자
    String getProviderId();     // 제공자 발급 아이디
    String getEmail();      // 이메일
    String getName();       // 사용자 이름
}
