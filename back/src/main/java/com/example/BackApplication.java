package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {
        "com.example.domain.Attraction.mapper",
        "com.example.domain.SiGunguContent.sigungumapper",
        "com.example.domain.member.mapper",
        "com.example.domain.article.mapper",
        "com.example.domain.trip.mapper",
        "com.example.domain.tripdetail.mapper",
})
@SpringBootApplication(scanBasePackages = {"com.example"})
public class BackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }
}
