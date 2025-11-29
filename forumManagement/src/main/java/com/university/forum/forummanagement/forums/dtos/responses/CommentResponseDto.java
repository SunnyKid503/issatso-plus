package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.models.Comment;
import com.university.forum.forummanagement.membership.dtos.responses.ForumMemberResponseDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommentResponseDto {
    private int id;
    private ForumMemberResponseDto author;
    private String content;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private List<CommentResponseDto> SubComments = new ArrayList<>();

    public CommentResponseDto() {
    }

    public CommentResponseDto(int id, ForumMemberResponseDto author, String content, Timestamp createdDate, Timestamp updatedDate, List<CommentResponseDto> subComments) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        SubComments = subComments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ForumMemberResponseDto getAuthor() {
        return author;
    }

    public void setAuthor(ForumMemberResponseDto author) {
        this.author = author;
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

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<CommentResponseDto> getSubComments() {
        return SubComments;
    }

    public void setSubComments(List<CommentResponseDto> subComments) {
        SubComments = subComments;
    }

    public static CommentResponseDto toCommentResponseDto(Comment comment)
    {
        return new CommentResponseDto(
                comment.getId(),
                ForumMemberResponseDto.toMemberResponseDto(comment.getAuthor()),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getUpdatedDate(),
                comment.getSubComments().stream()
                        .map(CommentResponseDto::toCommentResponseDto)
                        .collect(Collectors.toList())
        );
    }
}
