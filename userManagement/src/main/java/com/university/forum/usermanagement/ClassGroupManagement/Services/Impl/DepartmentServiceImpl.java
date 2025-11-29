package com.university.forum.usermanagement.ClassGroupManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.DepartmentRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Branch;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Department;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.DepartmentRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Services.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;


    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {


        Department department = new Department();
        department.setName(departmentRequestDto.getName());
        department.setReference(departmentRequestDto.getReference());
        department=departmentRepository.save(department);
        System.out.println("dep created : "+department.getReference());

        return convertToDepartmentResponseDto(department);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDepartmentResponseDto)
                .collect(Collectors.toList());
    }


    public DepartmentResponseDto getDepartmentById(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->
                new ElementNotFoundException("Department not found"));
        return convertToDepartmentResponseDto(department);
    }

    private DepartmentResponseDto convertToDepartmentResponseDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setReference(department.getReference());
        return dto;
    }

    @Override
    public List<DepartmentResponseDto> getBranchesByDepartmentId(int id) {
        Department department=departmentRepository.findById(id).orElseThrow(
                ()->new ElementNotFoundException("Department not found")
        );
        List<DepartmentResponseDto> branches=new ArrayList<>();
        for(Branch branch:department.getBranches()){
            branches.add(new DepartmentResponseDto(branch.getId(), branch.getName(), branch.getReference()));
        }
        return branches;
    }
}
