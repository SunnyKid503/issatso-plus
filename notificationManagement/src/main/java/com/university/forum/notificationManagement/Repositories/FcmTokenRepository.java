package com.university.forum.notificationManagement.Repositories;

import com.university.forum.notificationManagement.Models.FcmToken;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Integer> {

    FcmToken findByToken(String token);

    Iterable<FcmToken> findByMemberId(UUID userId);

    @Query("SELECT f FROM FcmToken f WHERE f.member.id IN :memberIds")
    Iterable<FcmToken> findAllByMemberIds(@Param("memberIds") List<UUID> memberIds);

    void deleteByToken(String token);

}
