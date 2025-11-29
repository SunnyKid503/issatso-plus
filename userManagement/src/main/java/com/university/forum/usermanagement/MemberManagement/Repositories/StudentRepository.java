package com.university.forum.usermanagement.MemberManagement.Repositories;

import com.university.forum.usermanagement.MemberManagement.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByAddressEmail(String email);
}
