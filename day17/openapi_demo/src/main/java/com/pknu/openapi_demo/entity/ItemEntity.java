package com.pknu.openapi_demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "busan_food_kr")
@Getter
@Setter
@NoArgsConstructor
public class ItemEntity {

    @Id
    private int UC_SEQ;

    private String MAIN_TITLE;
    private String GUGUN_NM;
    private double LAT;
    private double LNG;
    private String PLACE;
    private String TITLE;
    private String SUBTITLE;
    private String ADDR1;
    private String ADDR2;
    private String CNTCT_TEL;
    private String HOMEPAGE_URL;
    private String USAGE_DAY_WEEK_AND_TIME;
    private String RPRSNTV_MENU;
    private String MAIN_IMG_NORMAL;
    private String MAIN_IMG_THUMB;
    private String ITEMCNTNTS;
}
