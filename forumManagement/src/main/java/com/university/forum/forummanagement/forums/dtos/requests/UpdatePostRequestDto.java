package com.university.forum.forummanagement.forums.dtos.requests;

import com.university.forum.forummanagement.forums.models.PostType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdatePostRequestDto {
    @NotNull
    private int postId;
    @NotBlank(message = "Content should not be blank")
    private String content;
    @NotBlank(message = "Title should not be blank")
    private String title;
    private List<Integer> categoryIds = new ArrayList<>();
    @NotNull(message = "Post type should not be null")
    private PostType postType;
    private List<Integer> tagIds = new ArrayList<>();
    private MultipartFile[] files = new MultipartFile[0];
    private List<String> oldFilesUrls;

    public UpdatePostRequestDto() {
    }

    public UpdatePostRequestDto(int postId, String content, String title, List<Integer> categoryIds, PostType postType, List<Integer> tagIds, MultipartFile[] files) {
        this.postId = postId;
        this.content = content;
        this.title = title;
        this.categoryIds = categoryIds;
        this.postType = postType;
        this.tagIds = tagIds;
        this.files = files;
    }

    public List<String> getOldFilesUrls() {
        return oldFilesUrls;
    }

    public void setOldFilesUrls(List<String> oldFilesUrls) {
        this.oldFilesUrls = oldFilesUrls;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
