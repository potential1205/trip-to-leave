package com.example.domain.member.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetReq {

    private String email;
    private String authCode;
    private String password;
}
