package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.models.*;
import com.university.forum.forummanagement.membership.dtos.responses.ForumMemberResponseDto;

import java.sql.Timestamp;
import java.util.List;

public class AnnouncementResponseDto {
    private int id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<String> files;
    private String longDescription;
    private ForumMemberResponseDto author;
    private List<TaggableObjectResponseDto> tags;

    public AnnouncementResponseDto() {
    }

    public AnnouncementResponseDto(int id, String title, String content, Timestamp createdAt, Timestamp updatedAt, List<String> files, String longDescription, ForumMemberResponseDto author, List<TaggableObjectResponseDto> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.files = files;
        this.longDescription = longDescription;
        this.author = author;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public ForumMemberResponseDto getAuthor() {
        return author;
    }

    public void setAuthor(ForumMemberResponseDto author) {
        this.author = author;
    }

    public List<TaggableObjectResponseDto> getTags() {
        return tags;
    }

    public void setTags(List<TaggableObjectResponseDto> tags) {
        this.tags = tags;
    }

    public static AnnouncementResponseDto toAnnouncementResponseDto(Announcement announcement)
    {
        return new AnnouncementResponseDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt(),
                announcement.getFiles().stream().map(FileReference::getImageUrl).toList(),
                announcement.getLongDescription(),
                ForumMemberResponseDto.toMemberResponseDto(announcement.getAuthor()),
                announcement.getTags().stream().map(TaggableObjectResponseDto::create).toList()
        );
    }
}
