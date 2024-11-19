package com.example.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Integer memberId;
    private String username;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
}
