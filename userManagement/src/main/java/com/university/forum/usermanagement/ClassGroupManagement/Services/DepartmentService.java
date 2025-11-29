package com.university.forum.usermanagement.ClassGroupManagement.Services;

import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.DepartmentRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);

    List<DepartmentResponseDto> getAllDepartments();


    List<DepartmentResponseDto> getBranchesByDepartmentId(int id);
}
