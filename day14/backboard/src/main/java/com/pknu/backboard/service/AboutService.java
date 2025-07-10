package com.pknu.backboard.service;

import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.About;
import com.pknu.backboard.repository.AboutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  // Lombok에서 파라미터 포함된 생성자를 자동생성
public class AboutService {

    private final AboutRepository aboutRepository;

    // @RequiredArgsConstructor
    // public AboutService(AboutRepository aboutRepository) {
    //     this.aboutRepository = aboutRepository;
    // }

    public About getAbout() {
        About about = this.aboutRepository.findAll().get(0);

        return about;
    }
}
