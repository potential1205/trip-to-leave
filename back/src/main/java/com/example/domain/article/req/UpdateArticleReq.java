package com.example.domain.article.req;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleReq {
    private String title; // 제목
    private String category; // 카테고리 (공지사항, 오류, 문의 등)
    private String content; // 게시글 내용
    private List<MultipartFile> files;
}
