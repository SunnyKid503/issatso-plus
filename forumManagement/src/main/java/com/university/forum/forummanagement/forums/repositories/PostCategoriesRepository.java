package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.models.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoriesRepository extends JpaRepository<PostCategory, Integer> {
    @Query("SELECT case when (count(*) > 0)  then true else false end " +
            "FROM PostCategory u WHERE UPPER(u.typename) LIKE UPPER(:typename)")
    public boolean existsByName(@Param("typename") String typename);
}
