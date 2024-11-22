package com.example.domain.trip.controller;

import com.example.domain.trip.dto.TripArticleDto;
import com.example.domain.trip.service.TripArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/trips/articles")
@CrossOrigin(origins = "http://localhost:5173")
public class TripArticleController {

    private final TripArticleService tripArticleService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<TripArticleDto>> getAllTripArticles() {
        List<TripArticleDto> tripArticles = tripArticleService.getAllTripArticles();
        return ResponseEntity.ok(tripArticles);
    }

    // 해시태그 검색
    @GetMapping("/search-by-hashtag")
    public ResponseEntity<List<TripArticleDto>> searchByHashtag(@RequestParam String hashtag) {
        List<TripArticleDto> tripArticles = tripArticleService.searchArticlesByHashtag(hashtag);
        System.out.println(hashtag);
        return ResponseEntity.ok(tripArticles);
    }

    // 키워드 검색
    @GetMapping("/search-by-keyword")
    public ResponseEntity<List<TripArticleDto>> searchByKeyword(@RequestParam String keyword) {
        List<TripArticleDto> tripArticles = tripArticleService.searchTripArticles(keyword);
        return ResponseEntity.ok(tripArticles);
    }

    // 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> addLike(@PathVariable int id) {
        tripArticleService.addLikeToTripArticle(id);
        return ResponseEntity.ok().build();
    }

    // 조회수
    @PostMapping("/{id}/view")
    public ResponseEntity<Void> incrementView(@PathVariable int id) {
        tripArticleService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }
}
