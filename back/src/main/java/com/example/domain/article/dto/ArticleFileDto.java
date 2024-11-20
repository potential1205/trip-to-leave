package com.example.domain.article.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleFileDto {
    private String fileName; // 파일 이름
    private String filePath; // 파일 경로
    private String contentType; // 파일 타입
}