package com.example.domain.article.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadArticlesReq {

    private int page;
    private int size;
}
