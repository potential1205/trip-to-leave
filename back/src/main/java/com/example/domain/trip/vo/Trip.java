package com.example.domain.trip.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Trip {
    private Integer tripId;
    private Integer memberId;
    private Integer attractionId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likes;
    private Integer overview;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String coverImage;
}