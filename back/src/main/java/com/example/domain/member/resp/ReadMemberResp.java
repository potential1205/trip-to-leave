package com.example.domain.member.resp;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadMemberResp {
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
