package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.forums.dtos.requests.PostInteractionRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.PostInteractionResponseDto;
import com.university.forum.forummanagement.forums.dtos.responses.PostResponseDto;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.models.UserPostInteraction;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;

import java.util.UUID;

public interface PostInteractionService {

    public PostInteractionResponseDto upVote (UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException;
    public PostInteractionResponseDto downVote (UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException;
    public PostInteractionResponseDto save (UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException;
    public PostInteractionResponseDto seen (UUID userId, PostInteractionRequestDto dto) throws ElementNotFoundException;

}
