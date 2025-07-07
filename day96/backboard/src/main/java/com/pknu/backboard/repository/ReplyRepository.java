package com.pknu.backboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.backboard.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // 내용은 필요없음. JpaRepository에 기능이 다 존재
}
