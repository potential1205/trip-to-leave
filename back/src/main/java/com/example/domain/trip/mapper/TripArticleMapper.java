package com.example.domain.trip.mapper;

import com.example.domain.trip.dto.TripArticleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripArticleMapper {

    List<TripArticleDto> getTripArticles();

    TripArticleDto getTripArticleById(@Param("id") int id);

    List<TripArticleDto> searchTripArticles(@Param("keyword") String keyword);

    List<TripArticleDto> searchArticlesByHashtag(@Param("hashtag") String hashtag);

    int incrementLikes(@Param("id") int id);

    int incrementViewCount(@Param("id") int id);
}
