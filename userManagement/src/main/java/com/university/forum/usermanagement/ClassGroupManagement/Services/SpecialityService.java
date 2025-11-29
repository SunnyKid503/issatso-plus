package com.university.forum.usermanagement.ClassGroupManagement.Services;

import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.SpecialityRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.SpecialityResponseDto;

import java.util.List;

public interface SpecialityService {
    SpecialityResponseDto createSpeciality(SpecialityRequestDto specialityRequestDto);

    List<SpecialityResponseDto> getAllSpecialities();

    SpecialityResponseDto getSpecialityById(int id);

    List<DepartmentResponseDto> getLevelsBySpecialityId(int id);
}
