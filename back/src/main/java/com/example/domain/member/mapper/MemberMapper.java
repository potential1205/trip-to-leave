package com.example.domain.member.mapper;

import com.example.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<Member> findAll();

    Member findById(int memberId);

    void insert(Member member);

    void update(Member member);

    void deleteById(int memberId);

    void save(Member member);
}