package com.example.domain.SiGunguContent.controller;

import com.example.domain.SiGunguContent.sigungumapper.SidoMapper;
import com.example.domain.SiGunguContent.vo.Sido;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/regions/sidos")
@CrossOrigin(origins = "http://localhost:5173")
public class SidoController {
    private final SidoMapper sidoMapper;

    @GetMapping
    public List<Sido> getAllSidos() {
        return sidoMapper.getAllSidos();
    }
}
