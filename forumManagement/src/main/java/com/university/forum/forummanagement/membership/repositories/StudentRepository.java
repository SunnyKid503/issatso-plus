package com.university.forum.forummanagement.membership.repositories;

import com.university.forum.forummanagement.membership.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

}
