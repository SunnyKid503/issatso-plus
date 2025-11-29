package com.university.forum.usermanagement.ClassGroupManagement.Repositories;

import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, Integer> {
}
