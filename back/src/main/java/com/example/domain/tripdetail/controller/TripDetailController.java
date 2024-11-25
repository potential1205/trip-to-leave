package com.example.domain.tripdetail.controller;

import com.example.domain.global.resp.SuccessResp;
import com.example.domain.trip.dto.TripFileDto;
import com.example.domain.tripdetail.req.CreateTripReq;
import com.example.domain.tripdetail.dto.TripDetailDto;
import com.example.domain.tripdetail.req.UpdateTripReq;
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

    @PatchMapping(value="",consumes = {"multipart/form-data"})
    public ResponseEntity<SuccessResp> updateTripDetail(@ModelAttribute UpdateTripReq req, HttpServletRequest request) {

        System.out.println(req.toString());

        tripDetailService.updateTripDetail(req, request);

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDetailDto> getTripDetail(@PathVariable int tripId) {

        TripDetailDto tripDetailDto = tripDetailService.getTripDetail(tripId);

        for(TripFileDto dto : tripDetailDto.getImages()) {
            System.out.println(dto.toString());
        }

        return ResponseEntity.ok(
                tripDetailDto
        );
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<SuccessResp> deleteTripDetail(@PathVariable int tripId, HttpServletRequest request) {

        tripDetailService.deleteTripDetail(tripId, request);

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }
}
