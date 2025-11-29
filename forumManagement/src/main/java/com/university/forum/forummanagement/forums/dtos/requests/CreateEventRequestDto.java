package com.university.forum.forummanagement.forums.dtos.requests;

import com.university.forum.forummanagement.forums.models.Event;
import com.university.forum.forummanagement.forums.models.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateEventRequestDto{
    private int capacity;
    @NotBlank(message = "Content should not be blank")
    private String content;
    @NotBlank(message = "Title should not be blank")
    private String title;
    private List<Integer> categoryIds = new ArrayList<>();
    private MultipartFile[] files = new MultipartFile[0];
    private List<Integer> tagIds = new ArrayList<>();
    private String createdAt;
    private String startDate;
    private String endDate;
    private String longDescription;

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Timestamp getStartDate() {
        if(startDate == null || startDate.isEmpty())
            return null;

        return Timestamp.valueOf(
                startDate.replace("T", " ")
                        .replace("Z", " "));
    }

    public Timestamp getEndDate() {
        if(endDate == null || endDate.isEmpty())
            return null;

        return Timestamp.valueOf(
                endDate.replace("T", " ")
                        .replace("Z", " "));
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

    public CreateEventRequestDto() {
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

//    public MultipartFile getBanner() {
//        return banner;
//    }
//
//    public void setBanner(MultipartFile banner) {
//        this.banner = banner;
//    }
//    public Timestamp getCreatedAtAsTimestamp() {
//        if(createdAt == null || createdAt.isEmpty())
//            return null;
//
//        return Timestamp.valueOf(
//                createdAt.replace("T", " ")
//                        .replace("Z", " "));
//    }

}
