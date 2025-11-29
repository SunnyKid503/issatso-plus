package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.forums.dtos.requests.CreateEventRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.EventResponseDto;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;

import java.util.List;
import java.util.UUID;

public interface EventService {
    public List<EventResponseDto> get();
    public EventResponseDto create(UUID userId, CreateEventRequestDto dto) throws ElementNotFoundException, FileUploadException;
}
