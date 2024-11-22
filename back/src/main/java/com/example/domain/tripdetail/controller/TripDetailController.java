package com.example.domain.tripdetail.controller;

import com.example.domain.global.resp.SuccessResp;
import com.example.domain.tripdetail.req.CreateTripReq;
import com.example.domain.tripdetail.service.TripDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping("/tripdetail")
public class TripDetailController {

    private final TripDetailService tripDetailService;

    @PostMapping(value="",consumes = {"multipart/form-data"})
    public ResponseEntity<SuccessResp> createTripDetail(@ModelAttribute CreateTripReq req, HttpServletRequest request) {

        System.out.println(req.toString());

        tripDetailService.createTripDetail(req, request);

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }
}
