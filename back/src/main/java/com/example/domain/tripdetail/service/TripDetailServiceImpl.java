package com.example.domain.tripdetail.service;

import com.example.domain.ArticleFile;
import com.example.domain.Member;
import com.example.domain.Trip;
import com.example.domain.TripFile;
import com.example.domain.global.BusinessException;
import com.example.domain.member.mapper.MemberMapper;
import com.example.domain.tripdetail.mapper.TripDetailMapper;
import com.example.domain.tripdetail.mapper.TripFileMapper;
import com.example.domain.tripdetail.req.CreateTripReq;
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

    }
}
