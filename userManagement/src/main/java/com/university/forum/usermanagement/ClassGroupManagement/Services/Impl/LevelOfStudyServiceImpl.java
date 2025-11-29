package com.university.forum.usermanagement.ClassGroupManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.LevelOfStudyRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.SpecialityRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.BranchResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.LevelOfStudyResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.SpecialityResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Branch;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.ClassGroupManagement.Models.LevelOfStudy;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Speciality;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.LevelOfStudyRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.SpecialityRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Services.LevelOfStudyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelOfStudyServiceImpl implements LevelOfStudyService {

    private final LevelOfStudyRepository levelOfStudyRepository;
    private final SpecialityRepository specialityRepository;

    public LevelOfStudyServiceImpl(LevelOfStudyRepository levelOfStudyRepository, SpecialityRepository specialityRepository) {
        this.levelOfStudyRepository = levelOfStudyRepository;
        this.specialityRepository = specialityRepository;
    }

    @Override
    public LevelOfStudyResponseDto createLevelOfStudy(LevelOfStudyRequestDto levelOfStudyRequestDto) {
        Speciality speciality= specialityRepository.findById(levelOfStudyRequestDto.getSpecialityId()).orElseThrow(
                () -> new ElementNotFoundException("Speciality not found")
        );
        LevelOfStudy levelOfStudy=new LevelOfStudy();
        levelOfStudy.setName(levelOfStudyRequestDto.getName());
        levelOfStudy.setSpeciality(speciality);
        levelOfStudy.setReference(levelOfStudyRequestDto.getReference());

        speciality.getLevelsOfStudy().add(levelOfStudy);
        System.out.println("the speciality in the levelofstudy serv is : "+speciality);
        levelOfStudy= levelOfStudyRepository.save(levelOfStudy);

        return convertToLevelOfStudyResponseDto(levelOfStudy);
    }

    @Override
    public List<LevelOfStudyResponseDto> getAllLevelOfStudies() {
        List<LevelOfStudy> levelsOfStudy= levelOfStudyRepository.findAll();
        return levelsOfStudy.stream().map(this::convertToLevelOfStudyResponseDto).collect(Collectors.toList());
    }

    @Override
    public LevelOfStudyResponseDto getLevelOfStudyById(int id) {
        LevelOfStudy levelOfStudy=levelOfStudyRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("LevelOfStudy not found")
        );
        return convertToLevelOfStudyResponseDto(levelOfStudy);
    }

    @Override
    public List<DepartmentResponseDto> getGroupsByLevelId(int id) {
        LevelOfStudy level=levelOfStudyRepository.findById(id).orElseThrow(
                ()->new ElementNotFoundException("Level not found")
        );
        List<DepartmentResponseDto> groups=new ArrayList<>();
        for(ClassGroup classGroup: level.getClassGroups()){
            groups.add(new DepartmentResponseDto(classGroup.getId(), classGroup.getName(), classGroup.getReference()));
        }
        return groups;
    }

    private LevelOfStudyResponseDto convertToLevelOfStudyResponseDto(LevelOfStudy levelos) {
        LevelOfStudyResponseDto dto = new LevelOfStudyResponseDto();
        dto.setId(levelos.getId());
        dto.setName(levelos.getName());
        dto.setReference(levelos.getReference());
        dto.setSpeciality(convertToSpecialityResponseDto(levelos.getSpeciality()));
        return dto;
    }

    private SpecialityResponseDto convertToSpecialityResponseDto(Speciality speciality) {
        SpecialityResponseDto dto = new SpecialityResponseDto();
        dto.setId(speciality.getId());
        dto.setName(speciality.getName());
        dto.setReference(speciality.getReference());
        return dto;

    }
}
