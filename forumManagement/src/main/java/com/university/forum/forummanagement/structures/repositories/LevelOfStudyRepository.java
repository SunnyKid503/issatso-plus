package com.university.forum.forummanagement.structures.repositories;

import com.university.forum.forummanagement.structures.models.LevelOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelOfStudyRepository extends JpaRepository<LevelOfStudy, Integer> {
}
