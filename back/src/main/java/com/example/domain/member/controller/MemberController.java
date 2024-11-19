package com.example.domain.member.controller;

import com.example.domain.global.resp.SuccessResp;
import com.example.domain.member.req.*;
import com.example.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
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

    @PostMapping("/login")
    public ResponseEntity<SuccessResp> login(@RequestBody LoginReq req) {
        System.out.println("login");
        memberService.login(req.getEmail(), req.getPassword());

        return ResponseEntity.ok(
                new SuccessResp(true)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResp> logout() {

        memberService.logout();

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
    public ResponseEntity<SuccessResp> delete() {

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

}
