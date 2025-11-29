package com.university.forum.notificationManagement.Repositories;

import com.university.forum.notificationManagement.Models.NotificationMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationMemberRepository extends JpaRepository<NotificationMember, Integer> {


    Optional<NotificationMember> findByNotificationIdAndMemberId(Long notificationId,UUID memberId);
    Optional<List<NotificationMember>> findAllByNotificationIdInAndMemberId(List<Long> notificationIds, UUID memberId);

    List<NotificationMember> findByMemberIdOrderByNotificationCreatedAtDesc(UUID memberId, Pageable pageable);

    List<NotificationMember> findByMemberIdAndNotificationIdLessThanOrderByNotificationCreatedAtDesc(
            UUID memberId,
            Long lastNotificationId,
            Pageable pageable
    );

}


