package com.university.forum.forummanagement.forums.dtos.requests;

import com.university.forum.forummanagement.forums.models.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreatePostRequestDto {
    @NotBlank(message = "Content should not be blank")
    private String content;
    @NotBlank(message = "Title should not be blank")
    private String title;
    private List<Integer> categoryIds = new ArrayList<>();
    private String createdAt;
    @NotNull(message = "Post type should not be null")
    private PostType postType;
    private List<Integer> tagIds = new ArrayList<>();
    private MultipartFile[] files = new MultipartFile[0];

    public CreatePostRequestDto() {
    }

    public CreatePostRequestDto(String content, String title, List<Integer> categoryIds, String createdAt, PostType postType, List<Integer> tagIds, MultipartFile[] files) {
        this.content = content;
        this.title = title;
        this.categoryIds = categoryIds;
        this.createdAt = createdAt;
        this.postType = postType;
        this.tagIds = tagIds;
        this.files = files;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Timestamp getCreatedAt() {
        if(createdAt == null || createdAt.isEmpty())
            return null;

        return Timestamp.valueOf(
                createdAt.replace("T", " ")
                        .replace("Z", " "));
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}
