package com.example.domain.member.service;

import com.example.domain.Member;

public interface MemberService {
    void joinEmailAuth(String email);

    void join(String email, String authCode, String username, String password);

    Member login(String email, String password);

    void passwordResetEmailAuth(String email);

    void passwordReset(String email, String authCode, String password);

    void delete();

    void update(String username);

    void updatePassword(String password, String newPassword);

}
