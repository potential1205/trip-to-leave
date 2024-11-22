package com.example.domain.Attraction.mapper;

import com.example.domain.Attraction.controller.AttractionController;
import com.example.domain.Attraction.dto.AttractionDto;
import com.example.domain.Attraction.vo.Attraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AttractionDtoMapper {
    private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

    public AttractionDto toDto(Attraction attraction) {
        if (attraction == null) {
            return null;
        }

        logger.info("Mapping Attraction to DTO: firstImage1={}, firstImage2={}",
                attraction.getFirstImage1(), attraction.getFirstImage2());

        AttractionDto dto = new AttractionDto();
        dto.setAttractionId(attraction.getAttractionId());
        dto.setContentId(attraction.getContentId());
        dto.setTitle(attraction.getTitle());
        dto.setContentTypeId(attraction.getContentTypeId());
        dto.setAreaCode(attraction.getAreaCode());
        dto.setSiGunGuCode(attraction.getSiGunGuCode() != null ? attraction.getSiGunGuCode() : 0);
        dto.setFirstImage1(attraction.getFirstImage1());
        dto.setFirstImage2(attraction.getFirstImage2());
        dto.setMapLevel(attraction.getMapLevel());
        dto.setLatitude(attraction.getLatitude());
        dto.setLongitude(attraction.getLongitude());
        dto.setTel(attraction.getTel());
        dto.setAddr1(attraction.getAddr1());
        dto.setAddr2(attraction.getAddr2());
        dto.setHomepage(attraction.getHomepage());
        dto.setOverview(attraction.getOverview());

        return dto;
    }
}

