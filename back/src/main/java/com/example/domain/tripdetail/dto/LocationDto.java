package com.example.domain.tripdetail.dto;

import lombok.Data;

@Data
public class LocationDto {
    private Integer attractionId; // 관광지 ID
    private Integer contentId; // 콘텐츠 ID
    private String title; // 장소 이름
    private Integer contentTypeId; // 콘텐츠 유형 ID
    private Integer areaCode; // 지역 코드
    private Integer siGunGuCode; // 시군구 코드
    private String firstImage1; // 첫 번째 이미지 URL
    private String firstImage2; // 두 번째 이미지 URL
    private Integer mapLevel; // 지도 레벨
    private Double latitude; // 위도
    private Double longitude; // 경도
    private String tel; // 전화번호
    private String addr1; // 첫 번째 주소
    private String addr2; // 두 번째 주소
    private String homepage; // 홈페이지 URL
    private String overview; // 개요
}