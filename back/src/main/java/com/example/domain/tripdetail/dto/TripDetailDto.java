package com.example.domain.tripdetail.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TripDetailDto {
    private int memberId;
    private String title;
    private String content;
    private String author;
    private LocalDate startAt;
    private LocalDate endAt;
    private String headings;
    private MultipartFile coverImage;
}
