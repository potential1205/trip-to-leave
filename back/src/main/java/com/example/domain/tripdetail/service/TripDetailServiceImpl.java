package com.example.domain.tripdetail.service;

import com.example.domain.*;
import com.example.domain.global.BusinessException;
import com.example.domain.tripdetail.dto.LocationDto;
import com.example.domain.tripdetail.mapper.TripAttractionMapper;
import com.example.domain.tripdetail.mapper.TripDetailMapper;
import com.example.domain.tripdetail.mapper.TripFileMapper;
import com.example.domain.tripdetail.req.CreateTripReq;
import com.example.domain.tripdetail.dto.TripDetailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripDetailServiceImpl implements TripDetailService {

    private final TripDetailMapper tripDetailMapper;
    private final TripFileMapper tripFileMapper;
    private final TripAttractionMapper tripAttractionMapper;

    @Override
    @Transactional
    public void createTripDetail(CreateTripReq req, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session == null) {
            throw new RuntimeException("접근할 수 없습니다.");
        }

        Member member = (Member) session.getAttribute("member");

        Trip trip = Trip.builder()
                .memberId(member.getMemberId())
                .title(req.getTitle())
                .content(req.getContent())
                .startAt(String.valueOf(req.getStartAt()))
                .endAt(String.valueOf(req.getEndAt()))
                .overview(0)
                .likes(0)
                .headings(req.getHeadings())
                .build();

        tripDetailMapper.save(trip);

        int tripId = trip.getTripId();

        if (req.getHashTags()!=null && !req.getHashTags().isEmpty()) {
            tripDetailMapper.insertNewTags(req.getHashTags());

            List<Integer> hashtagIds = tripDetailMapper.findIdsByTags(req.getHashTags());

            for (int hashtagId : hashtagIds) {
                tripDetailMapper.insertTripHashtag(tripId, hashtagId);
            }
        }

        // 대표 이미지 저장 디렉토리 생성
        String uploadDir = "C:/uploads/trips/" + tripId + "/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // 첨부 파일 저장
        if (req.getCoverImage() != null && !req.getCoverImage().isEmpty()) {
            try {
                MultipartFile file = req.getCoverImage();

                String filePath = uploadDir + file.getOriginalFilename();

                File savedFile = new File(filePath);

                file.transferTo(savedFile); // 파일 저장

                TripFile tripFile = TripFile.builder()
                        .tripId(tripId)
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath)
                        .contentType(file.getContentType())
                        .fileType("COVER")
                        .build();

                tripFileMapper.insertTripFile(tripFile);

            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("대표 이미지 저장에 실패했습니다.");
            }
        }


        if (req.getLocations() != null) {
            for (List<Object> data : req.getLocations()) {
                if (!data.isEmpty()) {
                    if (data.get(0) instanceof String) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            LocationDto attraction = objectMapper.readValue((String) data.get(0), LocationDto.class);

                            TripAttraction tripAttraction = TripAttraction.builder()
                                    .tripId(tripId)
                                    .attractionId(attraction.getAttractionId())
                                    .build();

                            System.out.println(tripAttraction);

                            tripAttractionMapper.insertTripAttraction(tripAttraction);

                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new BusinessException("여행첩 관광지 정보를 등록하는데 실패했습니다.");
                        }
                    }
                }
            }
        }

        if (req.getImages() != null && !req.getImages().isEmpty()) {
            for (MultipartFile file : req.getImages()) {
                try{
                    String filePath = uploadDir + file.getOriginalFilename();

                    File savedFile = new File(filePath);

                    file.transferTo(savedFile);

                    TripFile tripFile = TripFile.builder()
                            .tripId(tripId)
                            .fileName(file.getOriginalFilename())
                            .filePath(filePath)
                            .contentType(file.getContentType())
                            .fileType("NORMAL")
                            .build();

                    tripFileMapper.insertTripFile(tripFile);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException("컨텐츠 이미지 저장에 실패했습니다.");
                }
            }
        }
    }

    @Override
    public TripDetailDto getTripDetail(int tripId) {
        TripDetailDto tripDetailDto = tripDetailMapper.findById(tripId);

        return tripDetailDto;
    }

    @Override
    public void deleteTripDetail(int tripId, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session == null) {
            throw new RuntimeException("접근할 수 없습니다.");
        }

        tripDetailMapper.deleteById(tripId);
    }
}
