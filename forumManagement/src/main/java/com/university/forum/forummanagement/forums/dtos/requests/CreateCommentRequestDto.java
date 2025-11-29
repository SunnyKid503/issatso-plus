package com.university.forum.forummanagement.forums.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class CreateCommentRequestDto {
    @NotBlank(message = "Content should not be blank")
    private String content;
    private Date createdDate;
    @Min(1)
    @NotNull(message = "post id should not be null")
    private int postId;
    private int parentCommentId;

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public CreateCommentRequestDto() {
    }

    public CreateCommentRequestDto(String content, Date createdDate, int postId) {
        this.content = content;
        this.createdDate = createdDate;
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
