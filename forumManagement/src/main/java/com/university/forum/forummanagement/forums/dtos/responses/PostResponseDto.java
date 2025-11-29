package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.forums.models.PostCategory;
import com.university.forum.forummanagement.forums.models.PostType;
import com.university.forum.forummanagement.membership.dtos.responses.ForumMemberResponseDto;

import java.sql.Timestamp;
import java.util.List;

public class PostResponseDto {
    private int id;
    private int upVoteCount;
    private int downVoteCount;
    private int viewCount;
    private String content;
    private String title;
    private ForumMemberResponseDto author;
    private List<PostCategory> categories;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private PostType postType;
    private List<TaggableObjectResponseDto> tags;

    private List<String> files;

    public PostResponseDto(int id, int upVoteCount, int downVoteCount, int viewCount, String content, String title, ForumMemberResponseDto author, List<PostCategory> categories, Timestamp createdAt, Timestamp updatedAt, PostType postType, List<TaggableObjectResponseDto> tags, List<String> files) {
        this.id = id;
        this.upVoteCount = upVoteCount;
        this.downVoteCount = downVoteCount;
        this.viewCount = viewCount;
        this.content = content;
        this.title = title;
        this.author = author;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postType = postType;
        this.tags = tags;
        this.files = files;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public PostResponseDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(int upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public int getDownVoteCount() {
        return downVoteCount;
    }

    public void setDownVoteCount(int downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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

    public ForumMemberResponseDto getAuthor() {
        return author;
    }

    public void setAuthor(ForumMemberResponseDto author) {
        this.author = author;
    }

    public List<PostCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PostCategory> categories) {
        this.categories = categories;
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

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public List<TaggableObjectResponseDto> getTags() {
        return tags;
    }

    public void setTags(List<TaggableObjectResponseDto> tags) {
        this.tags = tags;
    }



    public static PostResponseDto toPostResponseDto(Post post)
    {
        return new PostResponseDto(post.getId(),
                post.getUpVoteCount(),
                post.getDownVoteCount(),
                post.getViewCount(),
                post.getContent(),
                post.getTitle(),
                ForumMemberResponseDto.toMemberResponseDto(post.getAuthor()),
                post.getCategories(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getPostType(),
                post.getTags().stream().map(TaggableObjectResponseDto::create).toList(),
                post.getFiles().stream().map(FileReference::getImageUrl).toList());
    }
}
