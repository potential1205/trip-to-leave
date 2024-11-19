package com.example.domain.SiGunguContent.controller;

import com.example.domain.SiGunguContent.sigungumapper.GunGuMapper;
import com.example.domain.SiGunguContent.vo.GunGu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class GunGuController {
    private final GunGuMapper gunguMapper;

    @GetMapping("/v1/regions/sidos/{sidoCode}/guguns")
    public List<GunGu> getGugunsBySidoCode(@PathVariable("sidoCode") int sidoCode) {
        List<GunGu> gunGus = gunguMapper.getGunGusBySidoCode(sidoCode);
        return gunGus;
    }
}
