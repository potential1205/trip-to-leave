package com.example.domain.member.controller;

import com.example.domain.Member;
import com.example.domain.global.resp.SuccessResp;
import com.example.domain.member.req.*;
import com.example.domain.member.resp.ReadMemberResp;
import com.example.domain.member.resp.ReadNameResp;
import com.example.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join/email-auth")
    public ResponseEntity<SuccessResp> joinEmailAuth(@RequestBody EmailAuthReq req) {

        memberService.joinEmailAuth(req.getEmail());

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

    @GetMapping("/name")
    public ResponseEntity<ReadNameResp> readName(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("member");

        return ResponseEntity.ok(
                new ReadNameResp(member.getUsername())
        );
    }

    @GetMapping("")
    public ResponseEntity<ReadMemberResp> readMember(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("member");

        return ResponseEntity.ok(
                new ReadMemberResp(member.getUsername(), member.getEmail(), member.getCreatedAt())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResp> login(@RequestBody LoginReq req, HttpServletRequest request) {
        Member member = memberService.login(req.getEmail(), req.getPassword());

        request.getSession().invalidate();
        HttpSession session = request.getSession(true);

        session.setAttribute("member", member);
        session.setMaxInactiveInterval(300);

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResp> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session != null) {
            request.getSession().invalidate();
        }

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PostMapping("/password-reset/email-auth")
    public ResponseEntity<SuccessResp> passwordResetEmailAuth(@RequestBody EmailAuthReq req) {

        memberService.passwordResetEmailAuth(req.getEmail());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PostMapping("/password-reset")
    public ResponseEntity<SuccessResp> passwordReset(@RequestBody PasswordResetReq req) {

        memberService.passwordReset(req.getEmail(), req.getAuthCode(), req.getPassword());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @DeleteMapping("")
    public ResponseEntity<SuccessResp> delete(HttpServletRequest request) {

        HttpSession session = request.getSession();

        System.out.println(session.getAttribute("member"));

        memberService.delete();

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PatchMapping("")
    public ResponseEntity<SuccessResp> update(@RequestBody UpdateReq req) {

        memberService.update(req.getUsername());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PatchMapping("/password")
    public ResponseEntity<SuccessResp> updatePassword(@RequestBody UpdatePasswordReq req) {

        memberService.updatePassword(req.getPassword(), req.getNewPassword());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @GetMapping("/session")
    public ResponseEntity<Boolean> readSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        boolean isLoggedIn = session != null && session.getAttribute("member") != null;

        return ResponseEntity.ok(isLoggedIn);
    }

}
