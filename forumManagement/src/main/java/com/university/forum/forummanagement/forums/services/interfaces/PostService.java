package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.forums.dtos.requests.CreatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.requests.UpdatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.FypPostResponseDto;
import com.university.forum.forummanagement.forums.dtos.responses.PostResponseDto;
import com.university.forum.forummanagement.forums.models.Post;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import org.hibernate.sql.Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PostService {
    public List<PostResponseDto> feed();
    public List<FypPostResponseDto> fyp(UUID userId) throws ElementNotFoundException;
    public PostResponseDto create(UUID userId, CreatePostRequestDto dto) throws ElementNotFoundException, FileUploadException;
    public List<PostResponseDto> saved(UUID userId) throws ElementNotFoundException;
    public List<PostResponseDto> postedBy(UUID userId) throws ElementNotFoundException;

    public PostResponseDto delete(UUID userId, int postId) throws ElementNotFoundException, IllegalOperationException;
    public PostResponseDto update(UUID userId, UpdatePostRequestDto dto) throws ElementNotFoundException, IllegalOperationException, FileUploadException;

    PostResponseDto getPostById(int postId) throws ElementNotFoundException;
}
