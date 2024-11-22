package com.example.domain.trip.service;

import com.example.domain.global.BusinessException;
import com.example.domain.trip.dto.TripArticleDto;
import com.example.domain.trip.dto.TripFileDto;
import com.example.domain.trip.mapper.TripArticleMapper;
import com.example.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripArticleServiceImpl implements TripArticleService {

    private final TripArticleMapper tripArticleMapper;
    private static final Logger logger = LoggerFactory.getLogger(TripArticleServiceImpl.class);

//    // 파일 경로 변환 함수
//    private String convertPath(String filePath) {
//        if (filePath.startsWith("C:/")) {
//            return filePath.replace("C:/uploads", "http://70.12.50.226:8080/uploads");
//        }
//        return filePath;
//    }
    private String convertPath(String filePath) {
        if (filePath.startsWith("C:/")) {
            return filePath.replace("C:/uploads", "http://localhost:8080/uploads");
        }
        return filePath;
}

    @Override
    public List<TripArticleDto> getAllTripArticles() {
        List<TripArticleDto> tripArticles = tripArticleMapper.getTripArticles();

        for (TripArticleDto tripArticle : tripArticles) {
            if (tripArticle.getFiles() != null && !tripArticle.getFiles().isEmpty()) {
                for (TripFileDto file : tripArticle.getFiles()) {
                    file.setFilePath(convertPath(file.getFilePath()));
                }
                TripFileDto coverImg = tripArticle.getFiles().get(0);
                logger.info("Cover image for Trip ID {}: {}", tripArticle.getTripId(), coverImg.getFilePath());
            } else {
                logger.warn("No files found for Trip ID {}", tripArticle.getTripId());
            }

            if (tripArticle.getHashtags() != null && !tripArticle.getHashtags().isEmpty()) {
                logger.info("Hashtags for Trip ID {}: {}", tripArticle.getTripId(), tripArticle.getHashtags());
            } else {
                logger.warn("No hashtags found for Trip ID {}", tripArticle.getTripId());
            }
        }

        if (tripArticles.isEmpty()) {
            logger.info("No trip articles found, returning empty list.");
        } else {
            logger.info("Found {} trip articles.", tripArticles.size());
        }

        return tripArticles;
    }

    @Override
    public TripArticleDto getTripArticleById(int id) {
        TripArticleDto tripArticle = tripArticleMapper.getTripArticleById(id);

        if (tripArticle == null) {
            logger.warn("No trip article found for ID: {}", id);
            throw new ResourceNotFoundException("No trip article found for ID: " + id);
        }

        logger.info("Found trip article: {}", tripArticle);
        return tripArticle;
    }

    @Override
    public List<TripArticleDto> searchTripArticles(String keyword) {
        List<TripArticleDto> tripArticles = tripArticleMapper.searchTripArticles(keyword);

        for (TripArticleDto tripArticle : tripArticles) {
            if (tripArticle.getFiles() != null && !tripArticle.getFiles().isEmpty()) {
                for (TripFileDto file : tripArticle.getFiles()) {
                    file.setFilePath(convertPath(file.getFilePath()));
                }
            }
        }

        if (tripArticles.isEmpty()) {
            logger.info("No articles found for keyword '{}'", keyword);
        } else {
            logger.info("Found {} articles for keyword '{}'", tripArticles.size(), keyword);
        }

        return tripArticles;
    }

    @Override
    public List<TripArticleDto> searchArticlesByHashtag(String hashtag) {
        List<TripArticleDto> tripArticles = tripArticleMapper.searchArticlesByHashtag(hashtag);

        for (TripArticleDto tripArticle : tripArticles) {
            if (tripArticle.getFiles() != null && !tripArticle.getFiles().isEmpty()) {
                for (TripFileDto file : tripArticle.getFiles()) {
                    file.setFilePath(convertPath(file.getFilePath()));
                }
            }
        }
        if (tripArticles.isEmpty()) {
            logger.info("No articles found for hashtag '{}'", hashtag);
        } else {
            logger.info("Found {} articles for hashtag '{}'", tripArticles.size(), hashtag);
        }
        return tripArticles;
    }

    @Override
    public void addLikeToTripArticle(int id) {
        int rowsAffected = tripArticleMapper.incrementLikes(id);

        if (rowsAffected == 0) {
            logger.warn("Failed to add like to trip article with ID: {}", id);
            throw new ResourceNotFoundException("Trip article not found for ID: " + id);
        }

        logger.info("Added like to trip article with ID: {}", id);
    }

    @Override
    public void addLikeToTripArticle(int tripId, int memberId) {
        // 좋아요 중복 여부 확인
        Integer likeCount = tripArticleMapper.findLike(tripId, memberId);
        if (likeCount != null && likeCount > 0) {
            throw new BusinessException("이미 좋아요를 누른 게시글입니다.");
        }

        // 좋아요 추가
        tripArticleMapper.insertLike(tripId, memberId);

        // 좋아요 수 증가
        tripArticleMapper.incrementLikes(tripId);
    }

    @Override
    public void incrementViewCount(int id) {
        int rowsAffected = tripArticleMapper.incrementViewCount(id);

        if (rowsAffected == 0) {
            logger.warn("Failed to increment view count for trip article with ID: {}", id);
            throw new ResourceNotFoundException("Trip article not found for ID: " + id);
        }

        logger.info("Incremented view count for trip article with ID: {}", id);
    }

    @Override
    public List<TripArticleDto> getArticlesByMemberId(int memberId) {
        List<TripArticleDto> articles = tripArticleMapper.getArticlesByMemberId(memberId);

        for (TripArticleDto article : articles) {
            if (article.getFiles() != null && !article.getFiles().isEmpty()) {
                for (TripFileDto file : article.getFiles()) {
                    file.setFilePath(convertPath(file.getFilePath()));
                }
            }
        }

        return articles;
    }
}
