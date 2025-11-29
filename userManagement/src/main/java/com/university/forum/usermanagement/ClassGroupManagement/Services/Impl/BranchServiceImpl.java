package com.university.forum.usermanagement.ClassGroupManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.BranchRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.BranchResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Branch;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Department;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Speciality;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.BranchRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.DepartmentRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Services.BranchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    public BranchServiceImpl(BranchRepository branchRepository, DepartmentRepository departmentRepository) {
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public BranchResponseDto createBranch(BranchRequestDto branchRequestDto) {

        Department department =departmentRepository.findById(branchRequestDto.getDepartmentId()).orElseThrow(
                ()-> new ElementNotFoundException("Department not found")
        ) ;

        Branch branch = new Branch();
        branch.setName(branchRequestDto.getName());
        branch.setReference(branchRequestDto.getReference());
        branch.setDepartment(department);

        department.getBranches().add(branch);
        System.out.println("old branches in dep are : "+department.getBranches());
        branch=branchRepository.save(branch);
        return  convertToBranchResponseDto(branch);
    }

    @Override
    public List<BranchResponseDto> getAllBranches() {
        List<Branch> branches=branchRepository.findAll();
        return branches.stream()
                .map(this::convertToBranchResponseDto)
                .collect(Collectors.toList());

    }

    @Override
    public BranchResponseDto getBranchById(int id) {
        Branch branch = branchRepository.findById(id).orElseThrow(
                ()-> new ElementNotFoundException("Branch not found")
        );
        return convertToBranchResponseDto(branch);
    }

    @Override
    public List<DepartmentResponseDto> getSpecialitiesByBranchId(int id) {
        Branch branch=branchRepository.findById(id).orElseThrow(
                ()->new ElementNotFoundException("Branch not found")
        );
        List<DepartmentResponseDto> specialities=new ArrayList<>();
        for(Speciality speciality: branch.getSpecialities()){
            specialities.add(new DepartmentResponseDto(speciality.getId(), speciality.getName(), speciality.getReference()));
        }
        return specialities;
    }

    private BranchResponseDto convertToBranchResponseDto(Branch branch) {
        BranchResponseDto dto = new BranchResponseDto();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setReference(branch.getReference());
        dto.setDepartment(convertToDepartmentResponseDto(branch.getDepartment()));
        return dto;
    }

    private DepartmentResponseDto convertToDepartmentResponseDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setReference(department.getReference());
        return dto;
    }
}
