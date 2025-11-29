package com.university.forum.forummanagement.forums.services.interfaces;

import com.university.forum.forummanagement.admin.dtos.requests.CreateCategoryRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.SimpleCategoryResponseDto;
import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;

import java.util.List;

public interface CategoryService {
    public List<SimpleCategoryResponseDto> get();
    public SimpleCategoryResponseDto delete(int id) throws ElementAlreadyExistsException, ElementNotFoundException;
    public SimpleCategoryResponseDto create(CreateCategoryRequestDto dto) throws ElementAlreadyExistsException;
}
