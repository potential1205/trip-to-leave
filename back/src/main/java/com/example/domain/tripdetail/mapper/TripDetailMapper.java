package com.example.domain.tripdetail.mapper;

import com.example.domain.Trip;
import com.example.domain.tripdetail.dto.HashtagDto;
import com.example.domain.tripdetail.dto.TripDetailDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripDetailMapper {
    void save(Trip trip);

    void insertNewTags(@Param("hashTags") List<String> hashTags);

    void insertTripHashtag(@Param("tripId") int tripId, @Param("hashtagId") int hashtagId);

    List<Integer> findIdsByTags(@Param("hashTags") List<String> hashTags);

    TripDetailDto findById(int tripId);

    void deleteById(int tripId);

    List<String> selectHashtagsByTripId(int tripId);
}
