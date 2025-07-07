package com.pknu.backboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.backboard.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // SELECT * FROM member WHERE username = ?
    Optional<Member> findByUsername(String username);   // 사용자이름으로 사용자 조회
    Optional<Member> findByEmail(String email);  // 이메일로 사용자 조회
}
