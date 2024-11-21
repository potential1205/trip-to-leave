package com.example.domain.article.service;

import com.example.domain.Article;
import com.example.domain.ArticleFile;
import com.example.domain.Member;
import com.example.domain.article.dto.FileDto;
import com.example.domain.article.dto.ReadArticleDto;
import com.example.domain.article.dto.ReadArticleResp;
import com.example.domain.article.mapper.ArticleFileMapper;
import com.example.domain.article.mapper.ArticleMapper;
import com.example.domain.article.req.CreateArticleReq;
import com.example.domain.article.req.UpdateArticleReq;
import com.example.domain.article.resp.ReadArticlesResp;
import com.example.domain.global.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleFileMapper articleFileMapper;

    @Override
    public ReadArticlesResp readArticles(int page, int size) {
        int offset = (page - 1) * size;

        List<ReadArticleDto> articleDtoList = articleMapper.findArticles(offset, size);

        int totalCount = articleMapper.countArticles();

        return ReadArticlesResp.builder()
                .articles(articleDtoList)
                .totalCount(totalCount)
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    @Override
    @Transactional
    public void createArticle(CreateArticleReq req, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session == null) {
            throw new RuntimeException("접근할 수 없습니다.");
        }

        Member member = (Member) session.getAttribute("member");

        Article article = Article.builder()
                .title(req.getTitle())
                .category(req.getCategory())
                .content(req.getContent())
                .createdAt(LocalDateTime.now())
                .overview(0)
                .category(req.getCategory())
                .memberId(member.getMemberId())
                .build();

        articleMapper.insertArticle(article);

        String uploadDir = "C:/uploads/" + article.getArticleId() + "/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // 첨부 파일 저장
        if (req.getFiles() != null && !req.getFiles().isEmpty()) {
            for (MultipartFile file : req.getFiles()) {
                try {
                    String filePath = uploadDir + file.getOriginalFilename();

                    File savedFile = new File(filePath);

                    file.transferTo(savedFile); // 파일 저장

                    ArticleFile articleFile = ArticleFile.builder()
                            .articleId(article.getArticleId())
                            .fileName(file.getOriginalFilename())
                            .filePath(filePath)
                            .contentType(file.getContentType())
                            .build();

                    articleFileMapper.insertFile(articleFile); // 파일 정보 저장
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException("파일 저장에 실패했습니다.");
                }
            }
        }
    }

    @Override
    public ReadArticleResp readArticle(int articleId) {
        ReadArticleDto articleDto = articleMapper.findArticle(articleId);

        List<ArticleFile> files = articleFileMapper.findArticleFiles(articleId);

        List<FileDto> fileDtos = files.stream()
                .map(file -> FileDto.builder()
                        .fileName(file.getFileName())
                        .downloadUrl("/articles/files/" + file.getFileId()) // 다운로드 URL 생성
                        .build())
                .toList();

        return new ReadArticleResp(articleDto, fileDtos);
    }

    @Override
    public void deleteArticle(int articleId) {
        articleMapper.deleteArticle(articleId);
    }

    @Override
    @Transactional
    public void updateArticle(int articleId, UpdateArticleReq req, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 확인

        if (session == null) {
            throw new RuntimeException("접근할 수 없습니다.");
        }

        // 게시글 업데이트
        Article updatedArticle = Article.builder()
                .articleId(articleId)
                .title(req.getTitle())
                .category(req.getCategory())
                .content(req.getContent())
                .build();

        articleMapper.updateArticle(updatedArticle);

        String uploadDir = "C:/uploads/" + articleId + "/";
        File uploadDirFile = new File(uploadDir);
        if (uploadDirFile.exists()) {
            deleteFolder(uploadDirFile); // 폴더 삭제
        }
        uploadDirFile.mkdirs(); // 폴더 재생성

        articleFileMapper.deleteById(articleId);

        // 새 파일 저장
        if (req.getFiles() != null && !req.getFiles().isEmpty()) {
            for (MultipartFile file : req.getFiles()) {
                try {
                    String filePath = uploadDir + file.getOriginalFilename();

                    File savedFile = new File(filePath);

                    file.transferTo(savedFile); // 파일 저장

                    ArticleFile articleFile = ArticleFile.builder()
                            .articleId(articleId)
                            .fileName(file.getOriginalFilename())
                            .filePath(filePath)
                            .contentType(file.getContentType())
                            .build();

                    articleFileMapper.insertFile(articleFile); // 파일 정보 저장
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException("파일 저장에 실패했습니다.");
                }
            }
        }
    }

    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file); // 재귀적으로 내부 폴더 삭제
                } else {
                    if (!file.delete()) {
                        throw new BusinessException("파일 삭제 실패: " + file.getName());
                    }
                }
            }
        }
        if (!folder.delete()) {
            throw new BusinessException("폴더 삭제 실패: " + folder.getAbsolutePath());
        }
    }


}
