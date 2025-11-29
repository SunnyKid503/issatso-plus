package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.forums.dtos.requests.CreateCommentRequestDto;
import com.university.forum.forummanagement.forums.dtos.requests.CreatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.CommentResponseDto;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CommentService {
    public List<CommentResponseDto> getForPost(int postId);
    public List<CommentResponseDto> getForUser(UUID userId);
    public List<CommentResponseDto> getForComment(int commentId);
    public CommentResponseDto create(UUID userId, CreateCommentRequestDto dto) throws ElementNotFoundException, IllegalOperationException;
}
