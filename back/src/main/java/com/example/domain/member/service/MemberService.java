package com.example.domain.member.service;

public interface MemberService {
    void sendEmailAuth(String email);

    void join(String email, String authCode, String username, String password);
}
