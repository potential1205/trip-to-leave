package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFile {
    private int fileId; // 파일 ID
    private int articleId; // 게시글 ID
    private String fileName; // 파일 이름
    private String filePath; // 파일 경로
    private String contentType; // 파일 타입 (MIME 타입)
}
