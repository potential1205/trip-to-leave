package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private int articleId; // 게시글 ID
    private int memberId; // 작성자 (회원 ID)
    private String title; // 제목
    private String category; // 카테고리 (공지사항, 오류, 문의 등)
    private String content; // 게시글 내용
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간
    private int overview; // 조회수
}
