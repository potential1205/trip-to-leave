package com.example.domain.tripdetail.service;

import com.example.domain.tripdetail.req.CreateTripReq;
import com.example.domain.tripdetail.dto.TripDetailDto;
import com.example.domain.tripdetail.req.UpdateTripReq;
import jakarta.servlet.http.HttpServletRequest;

public interface TripDetailService {
    void createTripDetail(CreateTripReq req, HttpServletRequest request);

    TripDetailDto getTripDetail(int tripId);

    void deleteTripDetail(int tripId, HttpServletRequest request);

    void updateTripDetail(UpdateTripReq req, HttpServletRequest request);
}
