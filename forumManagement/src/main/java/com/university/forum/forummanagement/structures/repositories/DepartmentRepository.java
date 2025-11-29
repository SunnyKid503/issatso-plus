package com.university.forum.forummanagement.structures.repositories;

import com.university.forum.forummanagement.structures.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
