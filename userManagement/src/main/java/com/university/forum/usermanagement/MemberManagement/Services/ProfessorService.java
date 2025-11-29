package com.university.forum.usermanagement.MemberManagement.Services;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ProfessorRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ProfessorUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.ProfessorResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProfessorService {
    ProfessorResponseDto createProfessor(ProfessorRequestDto professorRequestDto);

    List<ProfessorResponseDto> getAll();

    void updateStudent(UUID id, ProfessorUpdateRequestDto professorDto);
}
