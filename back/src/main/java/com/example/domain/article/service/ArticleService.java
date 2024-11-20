package com.example.domain.article.service;

import com.example.domain.article.dto.ReadArticleDto;
import com.example.domain.article.dto.ReadArticleResp;
import com.example.domain.article.req.CreateArticleReq;
import com.example.domain.article.resp.ReadArticlesResp;
import jakarta.servlet.http.HttpServletRequest;

public interface ArticleService {
    ReadArticlesResp readArticles(int page, int size);

    void createArticle(CreateArticleReq req, HttpServletRequest request);

    ReadArticleResp readArticle(int articleId);

    void deleteArticle(int articleId);
}
