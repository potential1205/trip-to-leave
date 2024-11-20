package com.example.domain.article.controller;

import com.example.domain.ArticleFile;
import com.example.domain.article.dto.ReadArticleDto;
import com.example.domain.article.dto.ReadArticleResp;
import com.example.domain.article.mapper.ArticleFileMapper;
import com.example.domain.article.req.CreateArticleReq;
import com.example.domain.article.req.ReadArticleReq;
import com.example.domain.article.req.ReadArticlesReq;
import com.example.domain.article.resp.ReadArticlesResp;
import com.example.domain.article.service.ArticleService;
import com.example.domain.global.resp.SuccessResp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleFileMapper articleFileMapper;

    @GetMapping("")
    public ResponseEntity<ReadArticlesResp> readArticles(
            @RequestParam(defaultValue = "1") int page,  // 기본값 설정
            @RequestParam(defaultValue = "10") int size) {

        ReadArticlesResp resp = articleService.readArticles(page, size);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ReadArticleResp> readArticle(@PathVariable("articleId") int articleId) {

        ReadArticleResp resp = articleService.readArticle(articleId);

        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<SuccessResp> deleteArticle(@PathVariable("articleId") int articleId) {

        articleService.deleteArticle(articleId);

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PostMapping(value="", consumes = {"multipart/form-data"})
    public ResponseEntity<SuccessResp> createArticle(
            @ModelAttribute CreateArticleReq req, HttpServletRequest request) {

        articleService.createArticle(req, request);

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) throws IOException {
        ArticleFile file = articleFileMapper.findArticleFile(fileId);

        Path path = Paths.get(file.getFilePath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }

}
