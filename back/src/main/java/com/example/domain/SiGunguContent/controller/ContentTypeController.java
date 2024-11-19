package com.example.domain.SiGunguContent.controller;

import com.example.domain.SiGunguContent.sigungumapper.ContentTypeMapper;
import com.example.domain.SiGunguContent.vo.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/contenttypes")
@RequiredArgsConstructor
public class ContentTypeController {
    private final ContentTypeMapper contentTypeMapper;

    @GetMapping
    public ResponseEntity<List<ContentType>> getAllContentTypes() {
        List<ContentType> contentTypes = contentTypeMapper.getAllContentTypes();
        return ResponseEntity.ok(contentTypes);
    }
}