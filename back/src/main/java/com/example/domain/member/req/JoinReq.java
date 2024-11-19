package com.example.domain.member.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinReq {
    private String email;
    private String authCode;
    private String username;
    private String password;
}
