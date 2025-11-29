package com.university.forum.usermanagement.MemberManagement.Services;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.StudentResponseDto;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);

    List<StudentResponseDto> getAll();

    void updateStudent(UUID id, StudentUpdateRequestDto studentRequestDto);

    StudentResponseDto getStudentById(UUID id);
}
