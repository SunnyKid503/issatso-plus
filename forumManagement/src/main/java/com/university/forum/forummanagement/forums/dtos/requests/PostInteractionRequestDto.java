package com.university.forum.forummanagement.forums.dtos.requests;

import java.util.UUID;

public class PostInteractionRequestDto {
    private int postId ;

    public PostInteractionRequestDto() {
    }
    public PostInteractionRequestDto(int postId) {
        this.postId = postId;
    }
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }


}
