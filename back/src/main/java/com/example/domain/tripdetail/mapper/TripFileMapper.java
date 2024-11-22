package com.example.domain.tripdetail.mapper;

import com.example.domain.TripFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TripFileMapper {

    void insertTripFile(TripFile tripFile);
}
