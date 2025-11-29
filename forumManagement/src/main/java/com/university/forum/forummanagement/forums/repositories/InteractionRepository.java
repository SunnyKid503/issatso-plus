package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.models.UserPostInteraction;
import com.university.forum.forummanagement.forums.models.UserPostInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<UserPostInteraction, UserPostInteractionId> {
}
