package com.example.domain.trip.service;

import com.example.domain.trip.dto.TripArticleDto;
import com.example.domain.trip.mapper.TripArticleMapper;
import com.example.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripArticleServiceImpl implements TripArticleService {

    private final TripArticleMapper tripArticleMapper;
    private static final Logger logger = LoggerFactory.getLogger(TripArticleServiceImpl.class); // 로거로 디버깅

    @Override
    public List<TripArticleDto> getAllTripArticles() {
        List<TripArticleDto> tripArticles = tripArticleMapper.getTripArticles();
        if (tripArticles.isEmpty()) {
            logger.info("No trip articles found, returning empty list");
        }
        logger.info("Found {} trip articles", tripArticles.size());
        return tripArticles; // 빈 배열 반환
    }

    @Override
    public TripArticleDto getTripArticleById(int id) {
        TripArticleDto tripArticle = tripArticleMapper.getTripArticleById(id);
        if (tripArticle == null) {
            logger.warn("No trip article found for ID: {}", id);
            throw new ResourceNotFoundException("No trip article found for ID: " + id);
        }
        logger.info("Found trip article {}", tripArticle);
        return tripArticle;
    }

    @Override
    public List<TripArticleDto> searchTripArticles(String keyword) {
        List<TripArticleDto> tripArticles = tripArticleMapper.searchTripArticles(keyword);
        if (tripArticles.isEmpty()) {
            logger.info("No trip articles found for keyword: {}, returning empty list", keyword);
        }
        logger.info("Found {} trip articles for keyword: {}", tripArticles.size(), keyword);
        return tripArticles; // 빈 배열 반환
    }

    @Override
    public void addLikeToTripArticle(int id) {
        int rowsAffected = tripArticleMapper.incrementLikes(id);
        if (rowsAffected == 0) {
            logger.warn("Failed to add like to trip article {}", id);
            throw new ResourceNotFoundException("Failed to add like to trip article " + id);
        }
    }

    @Override
    public void incrementViewCount(int id) {
        int rowsAffected = tripArticleMapper.incrementViewCount(id);
        if (rowsAffected == 0) {
            logger.warn("Failed to add view count to trip article {}", id);
            throw new ResourceNotFoundException("Failed to add view count to trip article " + id);
        }
    }
}
