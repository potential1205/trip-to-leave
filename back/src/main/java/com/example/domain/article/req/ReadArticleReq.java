package com.example.domain.article.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadArticleReq {
    private int articleId;
}
