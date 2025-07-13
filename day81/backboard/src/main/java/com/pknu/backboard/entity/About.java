package com.pknu.backboard.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String subtitle;

    @OneToMany(mappedBy = "about")
    private List<History> historyList;

    // /img/etc/background.png
    private String schoolImgPath;

    private String ourMission;
    
    private String ourVision;
}
