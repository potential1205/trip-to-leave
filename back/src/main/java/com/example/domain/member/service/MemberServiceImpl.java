package com.example.domain.member.service;

import com.example.domain.Member;
import com.example.domain.global.BusinessException;
import com.example.domain.global.DataBaseException;
import com.example.domain.member.mapper.MemberMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender javaMailSender;
    private final MemberMapper memberMapper;
    private final HttpSession session;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void joinEmailAuth(String email) {
        Member duplicatedMember = memberMapper.findByEmail(email);

        if (duplicatedMember != null) {
            throw new BusinessException("이미 사용중인 이메일입니다.");
        }

        Random random = new Random();
        String authCode = String.valueOf(100000 + random.nextInt(900000)); // 100000 ~ 999999 범위

        redisTemplate.opsForValue().set("join:email-auth:" + email, authCode, 5, TimeUnit.MINUTES);

        System.out.println(authCode);

//        String subject = "[Trip To Leave] 이메일 인증";
//        String content = String.format("인증번호는 %s 입니다.", authCode);
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setFrom(senderEmail);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(content);
//
//        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void join(String email, String authCode, String username, String password) {
        String storedToken = redisTemplate.opsForValue().get("join:email-auth:" + email);

        if (storedToken == null) {
            throw new BusinessException("존재하지 않거나 만료된 인증번호입니다.");
        }

        if (!authCode.equals(storedToken)) {
            throw new BusinessException("인증번호가 다릅니다.");
        }

        Member duplicatedMember = memberMapper.findByEmail(email);

        if (duplicatedMember != null) {
            throw new BusinessException("이미 사용중인 이메일입니다.");
        }

        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        memberMapper.save(member);

        redisTemplate.delete("join:email-auth:" + email);
    }

    @Override
    public void login(String email, String password) {
        Member member = memberMapper.findByEmailAndPassword(email, password);

        if (member == null) {
            throw new DataBaseException("존재하는 회원이 없거나 비밀번호가 일치하지않습니다");
        }

        session.setAttribute("member", member);
    }

    @Override
    public void logout() {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            throw new DataBaseException("접근할 수 없습니다.");
        }

        session.invalidate();
    }

    @Override
    public void passwordResetEmailAuth(String email) {
        Member findMember = memberMapper.findByEmail(email);

        System.out.println(email);

        System.out.println(findMember.getPassword());

        if (findMember == null) {
            throw new BusinessException("존재하지 않는 사용자입니다.");
        }

        Random random = new Random();
        String authCode = String.valueOf(100000 + random.nextInt(900000)); // 100000 ~ 999999 범위

        redisTemplate.opsForValue().set("password-reset:email-auth:" + email, authCode, 5, TimeUnit.MINUTES);

        System.out.println(authCode);

//        String subject = "[Trip To Leave] 비밀번호 재설정 이메일 인증";
//        String content = String.format("인증번호는 %s 입니다.", authCode);
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setFrom(senderEmail);
//        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setText(content);
//
//        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void passwordReset(String email, String authCode, String password) {
        Member findMember = memberMapper.findByEmail(email);

        if (findMember == null) {
            throw new BusinessException("존재하지 않는 사용자입니다.");
        }

        String storedToken = redisTemplate.opsForValue().get("password-reset:email-auth:" + email);

        if (storedToken == null) {
            throw new BusinessException("존재하지 않거나 만료된 인증번호입니다.");
        }

        if (!authCode.equals(storedToken)) {
            throw new BusinessException("인증번호가 다릅니다.");
        }

        memberMapper.resetPassword(email, password);
    }

    @Override
    public void delete() {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            throw new DataBaseException("접근할 수 없습니다.");
        }

        // 게시글, 여행첩 정보도 지워야함

        session.invalidate();

        memberMapper.delete(member.getMemberId());
    }

    @Override
    public void update(String username) {
        Member member = (Member) session.getAttribute("member");

        System.out.println(member.getMemberId());

        if (member == null) {
            throw new DataBaseException("접근할 수 없습니다.");
        }

        memberMapper.update(member.getMemberId(), username);

        member.setUsername(username);
        session.setAttribute("member", member);
    }

    @Override
    public void updatePassword(String password, String newPassword) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            throw new DataBaseException("접근할 수 없습니다.");
        }

        if (!password.equals(member.getPassword())) {
            throw new DataBaseException("비밀번호가 일치하지 않습니다.");
        }

        memberMapper.updatePassword(member.getMemberId(), newPassword);

        session.invalidate();
    }

}
