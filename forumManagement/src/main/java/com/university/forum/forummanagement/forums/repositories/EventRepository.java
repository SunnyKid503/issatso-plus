package com.university.forum.forummanagement.forums.repositories;

import com.university.forum.forummanagement.forums.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
