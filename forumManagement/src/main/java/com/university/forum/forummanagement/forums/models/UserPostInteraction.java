package com.university.forum.forummanagement.forums.models;

import com.university.forum.forummanagement.membership.models.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class UserPostInteraction {
    @EmbeddedId
    private UserPostInteractionId id;
    private boolean upVote;
    private boolean downVote;
    private boolean seen;
    private boolean saved;
    private Timestamp savedAt;
    private Timestamp votedAt;

    public UserPostInteraction() {
    }

    public UserPostInteraction(UserPostInteractionId id, boolean upVote, boolean downVote, boolean seen, boolean saved, Timestamp savedAt, Timestamp votedAt) {
        this.id = id;
        this.upVote = upVote;
        this.downVote = downVote;
        this.seen = seen;
        this.saved = saved;
        this.savedAt = savedAt;
        this.votedAt = votedAt;
    }

    public UserPostInteractionId getId() {
        return id;
    }

    public void setId(UserPostInteractionId id) {
        this.id = id;
    }

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

    public boolean isDownVote() {
        return downVote;
    }

    public void setDownVote(boolean downVote) {
        this.downVote = downVote;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }

    public Timestamp getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(Timestamp votedAt) {
        this.votedAt = votedAt;
    }
}

