package com.example.domain.member.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordReq {

    private String password;
    private String newPassword;
}
