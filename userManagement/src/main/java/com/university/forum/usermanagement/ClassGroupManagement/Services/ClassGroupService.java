package com.university.forum.usermanagement.ClassGroupManagement.Services;

import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.ClassGroupRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.ClassGroupResponseDto;

import java.util.List;

public interface ClassGroupService {
    ClassGroupResponseDto createClassGroup(ClassGroupRequestDto classGroupRequestDto);

    List<ClassGroupResponseDto> getAllClassGroups();

    ClassGroupResponseDto getClassGroupById(int id);
}
