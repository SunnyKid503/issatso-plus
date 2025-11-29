package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.models.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileReferenceRepository extends JpaRepository<FileReference, Integer> {
    @Query("SELECT u FROM FileReference u WHERE u.imageUrl IN :urls")
    public List<FileReference> getAllByUrls(@Param("urls") List<String> urls);
}
