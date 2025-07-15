package com.pknu.backboard.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.RequiredArgsConstructor;

// OAuth2User -> 소셜로그인
// UserDetails -> 일반로그인
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, UserDetails { 

    private final MemberDto memberDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;   // 사용 안함!
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
           @Override
           public String getAuthority() {
               return memberDto.getRole().toString();
           } 
        });

        return collection;
    }

    @Override
    public String getName() {
        return memberDto.getUsername();
    }

    public String getEmail() {
        return memberDto.getEmail();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return memberDto.getUsername();
    }
}
