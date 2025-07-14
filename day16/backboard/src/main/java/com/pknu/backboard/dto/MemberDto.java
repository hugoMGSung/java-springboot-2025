package com.pknu.backboard.dto;

import com.pknu.backboard.security.MemberRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private String username;

    private String email;

    private MemberRole role;

    private String provider;
}
