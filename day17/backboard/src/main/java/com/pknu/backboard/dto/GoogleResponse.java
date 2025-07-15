package com.pknu.backboard.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Reponse {

    private final Map<String, Object> attribute;
    
    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
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
