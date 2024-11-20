package com.example.domain.article.resp;

import com.example.domain.article.dto.ReadArticleDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadArticlesResp {
    private List<ReadArticleDto> articles; // 게시글 목록
    private int totalCount; // 총 게시글 수
    private int currentPage; // 현재 페이지
    private int pageSize; // 페이지당 게시글 수
}
