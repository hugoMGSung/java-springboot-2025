package com.pknu.backboard.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.pknu.backboard.security.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long mno;

    @Column(unique = true, length = 150)
    private String username;   // email로 사용

    @Column(nullable = false)
    private String password;

    @Column(unique = true, length = 150)
    private String email;      // username과 동일하지만

    // 역할(ADMIN, USER)
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MemberRole role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;
}
