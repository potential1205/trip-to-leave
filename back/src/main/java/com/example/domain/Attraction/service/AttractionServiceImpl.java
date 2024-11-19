package com.example.domain.Attraction.service;

import com.example.domain.Attraction.dto.AttractionDto;
import com.example.domain.Attraction.mapper.AttractionDtoMapper;
import com.example.domain.Attraction.mapper.AttractionMapper;
import com.example.domain.Attraction.vo.Attraction;
import com.example.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;
    private final AttractionDtoMapper dtoMapper;

    @Override
    public List<AttractionDto> getAreaAttractions(Integer areaCode) {
        List<Attraction> attractions = attractionMapper.getAreaAttractions(areaCode);
        if (attractions.isEmpty()) {
            throw new ResourceNotFoundException("지역들을 찾을 수 없습니다. 입력된 지역: " + areaCode);
        }
        return attractions.stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AttractionDto> getSigunguAttractions(Integer areaCode, Integer sigunguCode) {
        List<Attraction> attractions = attractionMapper.getSigunguAttractions(areaCode, sigunguCode);
        if (attractions.isEmpty()) {
            throw new ResourceNotFoundException("시군구를 찾을 수 없습니다. 입력된 시군구:" + sigunguCode);
        }
        return attractions.stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AttractionDto> getAreaContentAttractions(Integer areaCode, Integer contentTypeId) {
        List<Attraction> attractions = attractionMapper.getAreaContentAttractions(areaCode, contentTypeId);
        if (attractions.isEmpty()) {
            throw new ResourceNotFoundException("지역 또는 관광지 정보를 찾을 수 없습니다.");
        }
        return attractions.stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AttractionDto> getSigunguContentAttractions(Integer areaCode, Integer sigunguCode, Integer contentTypeId) {
        List<Attraction> attractions = attractionMapper.getSigunguContentAttractions(areaCode, sigunguCode, contentTypeId);
        if (attractions.isEmpty()) {
            throw new ResourceNotFoundException("시군구 또는 관광지 정보를 찾을 수 없습니다.");
        }
        return attractions.stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AttractionDto> searchAttractions(Integer areacode, Integer sigungucode, Integer contenttypeid, String keyword) {
        List<Attraction> attractions = attractionMapper.searchAttractions(areacode, sigungucode, contenttypeid, keyword);
        if (attractions.isEmpty()) {
            throw new ResourceNotFoundException("검색 결과가 없습니다.");
        }
        return attractions.stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }
}
