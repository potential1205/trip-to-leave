package com.example.domain.article.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileId;
    private String fileName; // 파일 이름
    private String downloadUrl; // 다운로드 경로
}