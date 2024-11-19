package com.example.domain.SiGunguContent.sigungumapper;

import com.example.domain.SiGunguContent.vo.GunGu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GunGuMapper {
    List<GunGu> getGunGusBySidoCode(@Param("sidoCode") int sidoCode);
}
