package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.models.InteractionActionType;

import java.util.UUID;

public class PostInteractionResponseDto {
    private int postId ;
    private UUID userId ;
    private InteractionActionType action ;

    public PostInteractionResponseDto() {
    }

    public PostInteractionResponseDto(int postId, UUID userId, InteractionActionType action) {
        this.postId = postId;
        this.userId = userId;
        this.action = action;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public InteractionActionType getAction() {
        return action;
    }

    public void setAction(InteractionActionType action) {
        this.action = action;
    }
}
