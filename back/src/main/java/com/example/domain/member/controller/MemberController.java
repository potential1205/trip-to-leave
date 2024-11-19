package com.example.domain.member.controller;

import com.example.domain.global.resp.SuccessResp;
import com.example.domain.member.req.EmailAuthReq;
import com.example.domain.member.req.JoinReq;
import com.example.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join/email-auth")
    public ResponseEntity<SuccessResp> sendEmailAuth(@RequestBody EmailAuthReq req) {

        memberService.sendEmailAuth(req.getEmail());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PostMapping("/join")
    public ResponseEntity<SuccessResp> join(@RequestBody JoinReq req) {

        memberService.join(req.getEmail(), req.getAuthCode(), req.getUsername(), req.getPassword());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

}
