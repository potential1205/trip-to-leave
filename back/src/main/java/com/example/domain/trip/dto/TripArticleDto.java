package com.example.domain.trip.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripArticleDto {
    private int tripId;
    private String title;
    private String startAt;
    private String endAt;
    private int likes;
    private int overview;
    private String authorName;
    private List<TripFileDto> files; // 파일 리스트
    private List<String> hashtags; // 해시태그 리스트
}