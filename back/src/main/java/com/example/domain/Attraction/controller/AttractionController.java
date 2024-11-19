package com.example.domain.Attraction.controller;


import com.example.domain.Attraction.dto.AttractionDto;
import com.example.domain.Attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;

    // 특정 지역 코드에 해당하는 관광명소 조회
    @GetMapping("/area/{areacode}")
    public ResponseEntity<List<AttractionDto>> getAreaAttractions(
            @PathVariable("areacode") Integer areaCode) {
        List<AttractionDto> attractions = attractionService.getAreaAttractions(areaCode);
        return ResponseEntity.ok(attractions);
    }

    // 특정 시군구 코드에 해당하는 관광명소 조회
    @GetMapping("/area/{areacode}/sigungu/{sigungucode}")
    public ResponseEntity<List<AttractionDto>> getSigunguAttractions(
            @PathVariable("areacode") Integer areaCode,
            @PathVariable("sigungucode") Integer sigunguCode) {
        List<AttractionDto> attractions = attractionService.getSigunguAttractions(areaCode, sigunguCode);
        return ResponseEntity.ok(attractions);
    }

    // 특정 지역코드에 해당하는 관광타입에 대한 관광지 조회
    @GetMapping("/area/{areacode}/contenttype/{contenttypeid}")
    public ResponseEntity<List<AttractionDto>> getAreaContentAttractions(
            @PathVariable("areacode") Integer areaCode,
            @PathVariable("contenttypeid") Integer contentTypeId) {
        List<AttractionDto> attractions = attractionService.getAreaContentAttractions(areaCode, contentTypeId);
        return ResponseEntity.ok(attractions);
    }

    // 특정 시군구 코드에 해당하는 관광타입에 대한 관광명소 조회
    @GetMapping("/area/{areacode}/sigungu/{sigungucode}/contenttype/{contenttypeid}")
    public ResponseEntity<List<AttractionDto>> getSigunguContentAttractions(
            @PathVariable("areacode") Integer areaCode,
            @PathVariable("sigungucode") Integer sigunguCode,
            @PathVariable("contenttypeid") Integer contentTypeId) {
        List<AttractionDto> attractions = attractionService.getSigunguContentAttractions(areaCode, sigunguCode,
                contentTypeId);
        return ResponseEntity.ok(attractions);
    }

    // 검색 후 마커 찍기
    @GetMapping("/search")
    public ResponseEntity<List<AttractionDto>> searchAttractions(
            @RequestParam Integer areacode,
            @RequestParam(required = false) Integer sigungucode,
            @RequestParam(required = false) Integer contenttypeid,
            @RequestParam(required = false) String keyword
    ) {
        List<AttractionDto> attractions = attractionService.searchAttractions(areacode, sigungucode, contenttypeid, keyword);
        return ResponseEntity.ok(attractions);
    }
}
