package com.example.domain.tripdetail.mapper;

import com.example.domain.TripAttraction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripAttractionMapper {

    void insertTripAttraction(TripAttraction tripAttraction);
}
