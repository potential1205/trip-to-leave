package com.example.domain.SiGunguContent.sigungumapper;

import com.example.domain.SiGunguContent.vo.Sido;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SidoMapper {
    List<Sido> getAllSidos();
}
