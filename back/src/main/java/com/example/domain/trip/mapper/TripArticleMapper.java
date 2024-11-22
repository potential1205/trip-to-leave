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

    // 좋아요 중복 여부 확인
    Integer findLike(@Param("tripId") int tripId, @Param("memberId") int memberId);

    // 좋아요 추가
    void insertLike(@Param("tripId") int tripId, @Param("memberId") int memberId);

    // 좋아요 수 증가
    int incrementLikes(@Param("tripId") int tripId);

    int incrementViewCount(@Param("id") int id);

    List<TripArticleDto> getArticlesByMemberId(@Param("memberId") int memberId);
}
