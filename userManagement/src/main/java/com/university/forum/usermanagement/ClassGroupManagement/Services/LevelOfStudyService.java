package com.university.forum.usermanagement.ClassGroupManagement.Services;

import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.LevelOfStudyRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.SpecialityRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.LevelOfStudyResponseDto;

import java.util.List;

public interface LevelOfStudyService {
    LevelOfStudyResponseDto createLevelOfStudy(LevelOfStudyRequestDto levelOfStudyRequestDto);

    List<LevelOfStudyResponseDto> getAllLevelOfStudies();

    LevelOfStudyResponseDto getLevelOfStudyById(int id);

    List<DepartmentResponseDto> getGroupsByLevelId(int id);
}
