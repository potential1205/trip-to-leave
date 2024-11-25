package com.example.domain.Attraction.controller;


import com.example.domain.Attraction.dto.AttractionDetailDto;
import com.example.domain.Attraction.dto.AttractionDto;
import com.example.domain.Attraction.service.AttractionService;
import com.example.domain.Attraction.vo.Attraction;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;
    private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

    @Value("${openai.api.key}") // API 키를 @Value로 주입
    private String openAiApiKey;

    // 특정 지역 코드에 해당하는 관광명소 조회
    @GetMapping("/area/{areacode}")
    public ResponseEntity<List<AttractionDto>> getAreaAttractions(
            @PathVariable("areacode") Integer areaCode) {
        List<AttractionDto> attractions = attractionService.getAreaAttractions(areaCode);
        return ResponseEntity.ok(attractions);
    }

    // 특정 시군구 코드에 해당하는 관광명소 조회
    @GetMapping("/area/{areacode}/sigungu/{sigungucode}")
    public ResponseEntity<List<AttractionDto>> getSigunguAttractions(
            @PathVariable("areacode") Integer areaCode,
            @PathVariable("sigungucode") Integer sigunguCode) {
        List<AttractionDto> attractions = attractionService.getSigunguAttractions(areaCode, sigunguCode);
        return ResponseEntity.ok(attractions);
    }

    // 특정 지역코드에 해당하는 관광타입에 대한 관광지 조회
    @GetMapping("/area/{areacode}/contenttype/{contenttypeid}")
    public ResponseEntity<List<AttractionDto>> getAreaContentAttractions(
            @PathVariable("areacode") Integer areaCode,
            @PathVariable("contenttypeid") Integer contentTypeId) {
        List<AttractionDto> attractions = attractionService.getAreaContentAttractions(areaCode, contentTypeId);
        return ResponseEntity.ok(attractions);
    }

    // 특정 시군구 코드에 해당하는 관광타입에 대한 관광명소 조회
    @GetMapping("/area/{areacode}/sigungu/{sigungucode}/contenttype/{contenttypeid}")
    public ResponseEntity<List<AttractionDto>> getSigunguContentAttractions(
            @PathVariable("areacode") Integer areaCode,
            @PathVariable("sigungucode") Integer sigunguCode,
            @PathVariable("contenttypeid") Integer contentTypeId) {
        List<AttractionDto> attractions = attractionService.getSigunguContentAttractions(areaCode, sigunguCode,
                contentTypeId);
        return ResponseEntity.ok(attractions);
    }

    // 검색 후 마커 찍기
    @GetMapping("/search")
    public ResponseEntity<List<AttractionDto>> searchAttractions(
            @RequestParam Integer areacode,
            @RequestParam(required = false) Integer sigungucode,
            @RequestParam(required = false) Integer contenttypeid,
            @RequestParam(required = false) String keyword
    ) {
        List<AttractionDto> attractions = attractionService.searchAttractions(areacode, sigungucode, contenttypeid, keyword);
        return ResponseEntity.ok(attractions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttractionDto> getAttractionById(@PathVariable("id") Integer id) {
        logger.info("Attraction 조회 요청: ID={}", id);
        AttractionDto attraction = attractionService.getAttractionById(id);

        logger.info("Attraction DTO to be returned: {}", attraction);

        return ResponseEntity.ok(attraction);
    }

    @GetMapping("/area")
    public ResponseEntity<List<AttractionDto>> getAreaAttractions(@RequestParam String keyword) {

        List<AttractionDto> attractions = attractionService.getAreaAttractionsByKeyword(keyword);

        return ResponseEntity.ok(attractions);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getAttractionDetails(@RequestBody Map<String, Object> requestBody) {
        try {
            String title = (String) requestBody.get("title");
            Double latitude = (Double) requestBody.get("lat");  // 올바른 타입으로 캐스팅
            Double longitude = (Double) requestBody.get("lng");
            String addr1 = (String) requestBody.get("addr1");
            String firstImage1 = (String) requestBody.get("firstImage1");

            String gptResponse = callGptApi(title, String.valueOf(latitude), String.valueOf(longitude));

            Map<String, Object> response = new HashMap<>();
            response.put("title", title);
            response.put("lat", latitude);
            response.put("lng", longitude);
            response.put("addr1", addr1);
            response.put("firstImage1", firstImage1);
            response.put("description", gptResponse);

            return ResponseEntity.ok(response);
        } catch (ClassCastException e) {
            logger.error("Type casting error in requestBody", e);
            return ResponseEntity.badRequest().body("Invalid data format in requestBody");
        } catch (Exception e) {
            logger.error("Error occurred while processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    // DTO 변환 메서드
    private AttractionDetailDto mapToDetailDto(Attraction attraction, String gptResponse) {
        AttractionDetailDto dto = new AttractionDetailDto();
        dto.setTitle(attraction.getTitle());
        dto.setLatitude(attraction.getLatitude());
        dto.setLongitude(attraction.getLongitude());
        dto.setFirstImage1(attraction.getFirstImage1());
        dto.setAddr1(attraction.getAddr1());
        dto.setDescription(gptResponse);
        return dto;
    }


    private String callGptApi(String title, String lat, String lng) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String prompt = String.format(
                "'%s'이라는 장소에 대해 자세히 설명해 주세요. 이 장소는 위도 %s, 경도 %s에 위치하고 있습니다. 이 장소의 특징, 역사적 의미에 대해 100자 내로 간단히 설명해주세요",
                title, lat, lng
        );

        System.out.println(prompt);
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4");
        body.put("messages", List.of(
                Map.of("role", "system", "content", "당신은 장소 정보를 제공하는 유용한 한국어 도우미입니다."),
                Map.of("role", "user", "content", prompt)
        ));
        body.put("max_tokens", 200);
        body.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                request,
                Map.class
        );

        // JSON 응답 처리
        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null) {
            throw new Exception("GPT API 응답이 비어 있습니다.");
        }

        // "choices" 필드에서 데이터를 가져옵니다.
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new Exception("GPT API 응답에 'choices' 데이터가 없습니다.");
        }

        // 첫 번째 선택지에서 "message" 객체를 가져옵니다.
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        if (message == null || !message.containsKey("content")) {
            throw new Exception("GPT API 응답에서 'message' 데이터를 찾을 수 없습니다.");
        }

        // "content" 필드를 가져옵니다.
        String content = (String) message.get("content");
        return content.trim();
    }

}
