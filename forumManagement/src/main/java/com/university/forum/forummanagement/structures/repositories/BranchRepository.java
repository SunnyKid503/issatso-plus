package com.university.forum.forummanagement.structures.repositories;

import com.university.forum.forummanagement.structures.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
