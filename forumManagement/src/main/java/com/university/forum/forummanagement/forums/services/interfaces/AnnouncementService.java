package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.forums.dtos.requests.CreateAnnouncementRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.AnnouncementResponseDto;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AnnouncementService {
    public List<AnnouncementResponseDto> get();
    public List<AnnouncementResponseDto> get(UUID userId, Set<String> roles) throws ElementNotFoundException, IllegalOperationException;
    public AnnouncementResponseDto create(UUID memberId, CreateAnnouncementRequestDto dto) throws ElementNotFoundException, FileUploadException;
}
