package com.example.domain.tripdetail.req;

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
public class CreateTripReq {
    private String title;
    private String content;
    private LocalDate startAt;
    private LocalDate endAt;
    private MultipartFile coverImage;
    private List<String> hashTags;

    private List<List<Object>> locations; // 장소 정보들
    private String headings;
    private List<MultipartFile> images;
}
