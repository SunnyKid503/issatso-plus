package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.dtos.database.FypPostDto;
import com.university.forum.forummanagement.forums.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    final String savedPostsQuery = "SELECT u FROM Post u " +
            "JOIN UserPostInteraction i ON u.id = i.id.postId AND i.saved = true " +
            "AND i.id.memberId = :userId AND u.isArchived = false " +
            "ORDER BY i.savedAt DESC";

    final String fypQuery = "SELECT p as post, u as userPostInteraction " +
            "FROM Post p LEFT JOIN UserPostInteraction u ON p.id = u.id.postId " +
            "AND u.id.memberId = :userId AND p.isArchived = false " +
            "ORDER BY p.createdAt DESC";

    final String postsForUserQuery = "SELECT u FROM Post u " +
            "WHERE u.author.id = :userId ORDER BY u.createdAt DESC";
    @Query(savedPostsQuery)
    List<Post> saved(@Param("userId") UUID userId);

    @Query(fypQuery)
    List<FypPostDto> fyp(@Param("userId") UUID userId);

    @Query(postsForUserQuery)
    List<Post> postedBy(@Param("userId") UUID userId);

}
