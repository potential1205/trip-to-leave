package com.example.domain.member.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    private String email;
    private String password;
}
