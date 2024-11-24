package com.example.domain.tripdetail.mapper;

import com.example.domain.TripFile;
import com.example.domain.trip.dto.TripFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripFileMapper {

    void insertTripFile(TripFile tripFile);

    TripFileDto findCoverFile(int tripId);

    List<TripFileDto> findNormalFile(int tripId);
}
