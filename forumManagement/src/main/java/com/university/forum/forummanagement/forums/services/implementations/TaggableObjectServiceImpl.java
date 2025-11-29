package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.forums.dtos.responses.TaggableObjectResponseDto;
import com.university.forum.forummanagement.forums.services.interfaces.TaggableObjectService;
import com.university.forum.forummanagement.structures.repositories.TaggableObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaggableObjectServiceImpl implements TaggableObjectService {
    protected final TaggableObjectRepository taggableObjectRepository;
    public TaggableObjectServiceImpl(TaggableObjectRepository taggableObjectRepository)
    {
        this.taggableObjectRepository = taggableObjectRepository;
    }
    @Override
    public List<TaggableObjectResponseDto> getAll() {
        return taggableObjectRepository.findAll().stream()
                .map(TaggableObjectResponseDto::create).toList();
    }

    @Override
    public List<TaggableObjectResponseDto> getByName(String name) {
        return taggableObjectRepository.searchByNameCaseInsensitive(name).stream()
                .map(TaggableObjectResponseDto::create).toList();
    }
}
