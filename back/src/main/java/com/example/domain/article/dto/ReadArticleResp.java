package com.example.domain.article.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReadArticleResp {
    private int articleId; // 게시글 ID
    private int memberId; // 작성자 (회원 ID)
    private String username; // 작성자 (회원 ID)
    private String title; // 제목
    private String category; // 카테고리 (공지사항, 오류, 문의 등)
    private String content; // 게시글 내용
    private LocalDateTime createdAt; // 생성 시간
    private int overview; // 조회
    private List<FileDto> files;

    public ReadArticleResp(ReadArticleDto articleDto, List<FileDto> fileDtos) {
        this.articleId = articleDto.getArticleId();
        this.memberId = articleDto.getMemberId();
        this.username = articleDto.getUsername();
        this.title = articleDto.getTitle();
        this.category = articleDto.getCategory();
        this.content = articleDto.getContent();
        this.createdAt = articleDto.getCreatedAt();
        this.overview = articleDto.getOverview();
        this.files = fileDtos;
    }
}