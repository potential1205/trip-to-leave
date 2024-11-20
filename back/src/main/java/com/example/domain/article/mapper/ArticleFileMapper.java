package com.example.domain.article.mapper;

import com.example.domain.ArticleFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleFileMapper {
    void insertFile(ArticleFile articleFile);

    List<ArticleFile> findArticleFiles(int articleId);

    ArticleFile findArticleFile(int fileId);
}
