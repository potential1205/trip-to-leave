package com.example.domain.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripArticleDto {

    private int tripId;
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String coverImage;
    private int likes;
    private int overview;
    private String authorName;

}
