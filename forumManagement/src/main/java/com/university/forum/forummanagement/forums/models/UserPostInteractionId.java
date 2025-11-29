package com.university.forum.forummanagement.forums.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class UserPostInteractionId implements Serializable {
    private UUID memberId;
    private int postId;

    public UserPostInteractionId(UUID memberId, int postId) {
        this.memberId = memberId;
        this.postId = postId;
    }

    public UserPostInteractionId() {
    }
}
