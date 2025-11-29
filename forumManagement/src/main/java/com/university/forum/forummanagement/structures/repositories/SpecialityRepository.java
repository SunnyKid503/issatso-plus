package com.university.forum.forummanagement.structures.repositories;

import com.university.forum.forummanagement.structures.models.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
}
