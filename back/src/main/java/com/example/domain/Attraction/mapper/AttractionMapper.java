package com.example.domain.Attraction.mapper;

import com.example.domain.Attraction.vo.Attraction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionMapper {

    List<Attraction> getAreaAttractions(@Param("areacode") Integer areaCode);

    List<Attraction> getSigunguAttractions(@Param("areacode") Integer areaCode, @Param("sigungucode") Integer sigunguCode);

    List<Attraction> getAreaContentAttractions(@Param("areacode") Integer areaCode, @Param("contenttypeid") Integer contentTypeId);

    List<Attraction> getSigunguContentAttractions(@Param("areacode") Integer areaCode, @Param("sigungucode") Integer sigunguCode, @Param("contenttypeid") Integer contentTypeId);

    List<Attraction> searchAttractions(@Param("areacode") Integer areacode, @Param("sigungucode") Integer sigungucode, @Param("contenttypeid") Integer contenttypeid, @Param("keyword") String keyword);
}
