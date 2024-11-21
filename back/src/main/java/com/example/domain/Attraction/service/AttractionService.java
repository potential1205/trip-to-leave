package com.example.domain.Attraction.service;

import com.example.domain.Attraction.dto.AttractionDto;

import java.util.List;

public interface AttractionService {

    List<AttractionDto> getAreaAttractions(Integer areaCode);

    List<AttractionDto> getSigunguAttractions(Integer areaCode, Integer sigunguCode);

    List<AttractionDto> getAreaContentAttractions(Integer areaCode, Integer contentTypeId);

    List<AttractionDto> getSigunguContentAttractions(Integer areaCode, Integer sigunguCode, Integer contentTypeId);

    List<AttractionDto> searchAttractions(Integer areacode, Integer sigungucode, Integer contenttypeid, String keyword);

    AttractionDto getAttractionById(Integer id);

    List<AttractionDto> getAreaAttractionsByKeyword(String keyword);
}
