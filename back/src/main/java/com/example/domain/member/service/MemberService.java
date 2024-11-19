package com.example.domain.member.service;

import com.example.domain.Member;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    void joinEmailAuth(String email);

    void join(String email, String authCode, String username, String password);

    Member login(String email, String password);

    void logout();

    void passwordResetEmailAuth(String email);

    void passwordReset(String email, String authCode, String password);

    void delete();

    void update(String username);

    void updatePassword(String password, String newPassword);
}
