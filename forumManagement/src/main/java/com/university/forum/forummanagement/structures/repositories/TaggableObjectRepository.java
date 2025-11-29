package com.university.forum.forummanagement.structures.repositories;

import com.university.forum.forummanagement.structures.models.TaggableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaggableObjectRepository extends JpaRepository<TaggableObject, Integer> {
    @Query("SELECT u FROM TaggableObject u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<TaggableObject> searchByNameCaseInsensitive(@Param("pattern") String pattern);
}
