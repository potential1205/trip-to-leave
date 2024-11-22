package com.example.domain.tripdetail.service;

import com.example.domain.tripdetail.req.CreateTripReq;
import jakarta.servlet.http.HttpServletRequest;

public interface TripDetailService {
    void createTripDetail(CreateTripReq req, HttpServletRequest request);
}
