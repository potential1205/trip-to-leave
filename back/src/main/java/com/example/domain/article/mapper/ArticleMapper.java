package com.example.domain.article.mapper;

import com.example.domain.Article;
import com.example.domain.article.dto.ReadArticleDto;
import com.example.domain.article.resp.ReadArticlesResp;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ArticleMapper {

    int countArticles();

    List<ReadArticleDto> findArticles(@Param("offset") int offset, @Param("size") int size);

    @Options(useGeneratedKeys = true, keyProperty = "articleId")
    void insertArticle(Article article);

    ReadArticleDto findArticle(int articleId);

    void deleteArticle(int articleId);

    void updateArticle(Article updatedArticle);
}
