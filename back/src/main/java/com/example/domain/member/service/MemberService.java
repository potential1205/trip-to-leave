package com.example.domain.member.service;

import io.lettuce.core.dynamic.annotation.Param;

public interface MemberService {
    void joinEmailAuth(String email);

    void join(String email, String authCode, String username, String password);

    void login(String email, String password);

    void logout();

    void passwordResetEmailAuth(String email);

    void passwordReset(String email, String authCode, String password);

    void delete();

    void update(String username);

    void updatePassword(String password, String newPassword);
}
