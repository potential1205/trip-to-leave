package com.example.domain.Attraction.vo;

import lombok.Data;

@Data
public class Attraction {
    private Integer attractionId;
    private Integer contentId;
    private String title;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer siGunGuCode;
    private String firstImage1 = ""; // 기본값 설정
    private String firstImage2 = ""; // 기본값 설정
    private Integer mapLevel;
    private Double latitude;
    private Double longitude;
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
}