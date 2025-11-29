package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.forums.dtos.responses.TaggableObjectResponseDto;

import java.util.List;

public interface TaggableObjectService {
    public List<TaggableObjectResponseDto> getAll();
    public List<TaggableObjectResponseDto> getByName(String name);
}
