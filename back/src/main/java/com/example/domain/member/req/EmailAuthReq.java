package com.example.domain.member.req;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthReq {

    private String email;
}
