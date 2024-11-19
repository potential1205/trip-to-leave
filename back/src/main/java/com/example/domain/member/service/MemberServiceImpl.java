package com.example.domain.member.service;

import com.example.domain.Member;
import com.example.domain.global.BusinessException;
import com.example.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender javaMailSender;
    private final MemberMapper memberMapper;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmailAuth(String email) {
        Random random = new Random();
        String authCode = String.valueOf(100000 + random.nextInt(900000)); // 100000 ~ 999999 범위

        redisTemplate.opsForValue().set("email-auth:" + email, authCode, 5, TimeUnit.MINUTES);

        String subject = "[Trip To Leave] 이메일 인증";
        String content = String.format("인증번호는 %s 입니다.", authCode);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("dlwogns1205@gmail.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void join(String email, String authCode, String username, String password) {
        String storedToken = redisTemplate.opsForValue().get("email-auth:" + email);

        if (storedToken == null) {
            throw new BusinessException("존재하지 않거나 만료된 인증번호입니다.");
        }

        if (!authCode.equals(storedToken)) {
            throw new BusinessException("인증번호가 다릅니다.");
        }

        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .role("USER")
                .build();

        memberMapper.save(member);

        redisTemplate.delete("email-auth:" + email);
    }

}
