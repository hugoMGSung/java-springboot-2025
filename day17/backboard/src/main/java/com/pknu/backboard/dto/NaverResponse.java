package com.pknu.backboard.dto;

import java.util.Map;

public class NaverResponse implements OAuth2Reponse {

    private final Map<String, Object> attribute;
  
    @SuppressWarnings("unchecked")
    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("reponse");  // 구글과의 차이점
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString(); // 리턴된 json에서 뽑아서 출력
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString(); // 리턴된 json에서 뽑아서 출력
    }

    @Override
    public String getName() {
        return attribute.get("name").toString(); // 리턴된 json에서 뽑아서 출력
    }

}
