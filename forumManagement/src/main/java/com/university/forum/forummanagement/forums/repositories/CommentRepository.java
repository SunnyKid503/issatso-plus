package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.models.Comment;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT u FROM Comment u WHERE u.post.id = :postId AND u.parentComment.id IS NULL")
    List<Comment> findByPost(@Param("postId") int postId);

    @Query("SELECT u FROM Comment u WHERE u.parentComment.id = :commentId")
    List<Comment> findByComment(@Param("commentId") int commentId);

    @Query("SELECT u FROM Comment u WHERE u.author.id = :memberId AND u.parentComment.id IS NULL ORDER BY u.createdDate DESC")
    List<Comment> findByAuthor(@Param("memberId") UUID memberId);
}
