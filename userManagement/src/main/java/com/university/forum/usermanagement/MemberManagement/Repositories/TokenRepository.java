package com.university.forum.usermanagement.MemberManagement.Repositories;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes.BaseUser;
import com.university.forum.usermanagement.MemberManagement.Models.Member;
import com.university.forum.usermanagement.MemberManagement.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findAllByUser(BaseUser user);
    Optional<Token> findByToken(String token);
    Optional<Token> findByTokenAndUser(String token, BaseUser user);
}
