package com.example.domain.SiGunguContent.sigungumapper;


import com.example.domain.SiGunguContent.vo.ContentType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentTypeMapper {
    List<ContentType> getAllContentTypes();
}