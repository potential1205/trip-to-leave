package com.example.domain.tripdetail.service;

import com.example.domain.Member;
import com.example.domain.Trip;
import com.example.domain.tripdetail.mapper.TripDetailMapper;
import com.example.domain.tripdetail.req.CreateTripReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripDetailServiceImpl implements TripDetailService {

    private final TripDetailMapper tripDetailMapper;

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
                .build();

        tripDetailMapper.save(trip);

        int tripId = trip.getTripId();

        tripDetailMapper.insertNewTags(req.getHashTags());

        List<Integer> hashtagIds = tripDetailMapper.findIdsByTags(req.getHashTags());

        for (int hashtagId : hashtagIds) {
            tripDetailMapper.insertTripHashtag(tripId, hashtagId);
        }
    }
}
