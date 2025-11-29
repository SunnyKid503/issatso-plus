package com.university.forum.usermanagement.MemberManagement.Repositories;

import com.university.forum.usermanagement.MemberManagement.Models.Ban;
import com.university.forum.usermanagement.MemberManagement.Models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BanRepository extends JpaRepository<Ban, Integer> {
    // Check if user has any active ban (including temporal)
    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END 
        FROM Ban b 
        WHERE b.bannedUser.id = :userId 
        AND b.isActive = true 
        AND (b.endDate IS NULL OR b.endDate > CURRENT_TIMESTAMP)
        """)
    boolean hasActiveBan(@Param("userId") UUID userId);

    List<Ban> findByBannedUserIdOrderByStartDateDesc(UUID userId);

    List<Ban> findByBannedByIdOrderByStartDateDesc(UUID adminId);

    boolean existsByBannedUserAndIsActiveTrue(Member member);
    List<Ban> findByBannedUserAndIsActiveTrue(Member member);
}
