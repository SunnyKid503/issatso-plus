package com.university.forum.usermanagement.ClassGroupManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.SpecialityRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.BranchResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.SpecialityResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Branch;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Department;
import com.university.forum.usermanagement.ClassGroupManagement.Models.LevelOfStudy;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Speciality;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.BranchRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.SpecialityRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Services.SpecialityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class SpecialityServiceImpl implements SpecialityService {


    private final SpecialityRepository specialityRepository;
    private final BranchRepository branchRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository, BranchRepository branchRepository) {
        this.specialityRepository = specialityRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public SpecialityResponseDto createSpeciality(SpecialityRequestDto specialityRequestDto) {

        Branch branch= branchRepository.findById(specialityRequestDto.getBranchId()).orElseThrow(
                () -> new ElementNotFoundException("Branch not found")
        );
        Speciality speciality=new Speciality();
        speciality.setName(specialityRequestDto.getName());
        speciality.setBranch(branch);
        speciality.setReference(specialityRequestDto.getReference());

        branch.getSpecialities().add(speciality);
        System.out.println("the branch in the spec serv is : "+branch);
       speciality= specialityRepository.save(speciality);

       return convertToSpecialityResponseDto(speciality);
    }

    @Override
    public List<SpecialityResponseDto> getAllSpecialities() {
        List<Speciality> specialities=specialityRepository.findAll();
        return specialities.stream().map(this::convertToSpecialityResponseDto).collect(Collectors.toList());
    }

    @Override
    public SpecialityResponseDto getSpecialityById(int id) {
        Speciality speciality=specialityRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Speciality not found")
        );
        return convertToSpecialityResponseDto(speciality);
    }

    @Override
    public List<DepartmentResponseDto> getLevelsBySpecialityId(int id) {
        Speciality speciality=specialityRepository.findById(id).orElseThrow(
                ()->new ElementNotFoundException("Speciality not found")
        );
        List<DepartmentResponseDto> levels=new ArrayList<>();
        for(LevelOfStudy levelOfStudy: speciality.getLevelsOfStudy()){
            levels.add(new DepartmentResponseDto(levelOfStudy.getId(), levelOfStudy.getName(), levelOfStudy.getReference()));
        }
        return levels;
    }


    private BranchResponseDto convertToBranchResponseDto(Branch branch) {
        BranchResponseDto dto = new BranchResponseDto();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setReference(branch.getReference());
        return dto;
    }

    private SpecialityResponseDto convertToSpecialityResponseDto(Speciality speciality) {
        SpecialityResponseDto dto = new SpecialityResponseDto();
        dto.setId(speciality.getId());
        dto.setName(speciality.getName());
        dto.setReference(speciality.getReference());
        dto.setBranch(convertToBranchResponseDto(speciality.getBranch()));
        return dto;

    }

}
