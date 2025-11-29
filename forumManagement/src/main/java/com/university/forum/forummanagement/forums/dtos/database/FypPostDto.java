package com.university.forum.forummanagement.forums.dtos.database;

import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.models.UserPostInteraction;
import org.springframework.beans.factory.annotation.Value;

public interface FypPostDto {
    public Post getPost();
    public UserPostInteraction getUserPostInteraction();
}
