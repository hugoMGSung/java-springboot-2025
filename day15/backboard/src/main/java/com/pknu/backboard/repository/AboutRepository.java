package com.pknu.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.backboard.entity.About;

@Repository
public interface AboutRepository extends JpaRepository<About, Long> {
    // 기본이기 때문에 추가 메서드가 필요없음
}
