package com.example.domain.member.mapper;

import com.example.domain.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface MemberMapper {

    void save(Member member);

    Member findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    Member findByEmail(@Param("email") String email);

    void resetPassword(@Param("email") String email, @Param("password") String password);

    void delete(@Param("memberId") int memberId);

    void update(@Param("memberId") int memberId, @Param("username") String username);

    void updatePassword(@Param("memberId") int memberId, @Param("newPassword") String newPassword);
}
