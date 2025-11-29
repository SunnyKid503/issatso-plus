package com.university.forum.forummanagement.structures.repositories;

import com.university.forum.forummanagement.structures.models.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Integer> {
}
