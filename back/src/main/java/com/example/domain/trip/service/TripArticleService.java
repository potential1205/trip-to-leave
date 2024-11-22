package com.example.domain.trip.service;


import com.example.domain.trip.dto.TripArticleDto;

import java.util.List;

public interface TripArticleService {

    List<TripArticleDto> getAllTripArticles();

    TripArticleDto getTripArticleById(int id);

    List<TripArticleDto> searchTripArticles(String keyword);

    void addLikeToTripArticle(int id);

    void incrementViewCount(int id);
}
