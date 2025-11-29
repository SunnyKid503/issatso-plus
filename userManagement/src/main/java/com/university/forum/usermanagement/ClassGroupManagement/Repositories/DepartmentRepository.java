package com.university.forum.usermanagement.ClassGroupManagement.Repositories;

import com.university.forum.usermanagement.ClassGroupManagement.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
