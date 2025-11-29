package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.models.Announcement;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT DISTINCT e FROM Announcement e " +
            "LEFT JOIN e.tags t WHERE t.id IN :userTags " +
            "OR e.tags IS EMPTY " +
            "ORDER BY e.CreatedAt DESC")
    List<Announcement> findByAnyTag(@Param("userTags") List<Integer> userTagIds);
}
