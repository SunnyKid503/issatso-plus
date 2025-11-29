package com.university.forum.forummanagement.forums.dtos.requests;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CreateAnnouncementRequestDto {
    @NotBlank(message = "Title should not be blank")
    private String title;
    @NotBlank(message = "Content should not be blank")
    private String content;
    private String createdAt;
    private String longDescription;
    private List<Integer> tagIds = new ArrayList<>();
    private MultipartFile[] files = new MultipartFile[0];

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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
