package com.pknu.backboard.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pknu.backboard.entity.About;
import com.pknu.backboard.entity.History;
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

        // ID값으로 또는 year로 오름차순 정렬을 다시하고 할당
        List<History> historyList = about.getHistoryList();
        historyList.sort(Comparator.comparing(History::getId));
        about.setHistoryList(historyList);

        return about;
    }

    public void putAbout(About about) {
        this.aboutRepository.save(about);

        System.out.println("저장완료!");
    }

    // 최신 내용이 없으면 빈값으로 리턴
    public About getAboutLatest() {
        // Order by DESC로 처리해도 됨
        Optional<About> opAbout = aboutRepository.findById(1L);

        if (opAbout.isPresent()) {
            return opAbout.get();
        } else {
            return new About();
        }
    }
}
