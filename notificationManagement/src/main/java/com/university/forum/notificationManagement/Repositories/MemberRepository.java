package com.university.forum.notificationManagement.Repositories;

import com.university.forum.notificationManagement.Models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByAddressEmail(String email);
    Optional<Member> findByAddressEmailOrId(String email,UUID id);
}
