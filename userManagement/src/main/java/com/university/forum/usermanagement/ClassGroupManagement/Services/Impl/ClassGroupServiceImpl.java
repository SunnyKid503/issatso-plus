package com.university.forum.usermanagement.ClassGroupManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.ClassGroupRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.ClassGroupResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.LevelOfStudyResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.SpecialityResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.ClassGroupManagement.Models.LevelOfStudy;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Speciality;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.ClassGroupRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Repositories.LevelOfStudyRepository;
import com.university.forum.usermanagement.ClassGroupManagement.Services.ClassGroupService;
import com.university.forum.usermanagement.ClassGroupManagement.Services.LevelOfStudyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassGroupServiceImpl implements ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final LevelOfStudyRepository levelOfStudyRepository;

    public ClassGroupServiceImpl(ClassGroupRepository classGroupRepository, LevelOfStudyRepository levelOfStudyRepository) {
        this.classGroupRepository = classGroupRepository;
        this.levelOfStudyRepository = levelOfStudyRepository;
    }


    @Override
    public ClassGroupResponseDto createClassGroup(ClassGroupRequestDto classGroupRequestDto) {
        LevelOfStudy levelOfStudy=levelOfStudyRepository.findById(classGroupRequestDto.getLevelOfStudyId()).orElseThrow(
                ()->new ElementNotFoundException("Level Of Study Not Found")
        );

        ClassGroup classGroup = new ClassGroup();
        classGroup.setName(classGroupRequestDto.getName());
        classGroup.setReference(classGroupRequestDto.getReference());
        classGroup.setLevelOfStudy(levelOfStudy);
        levelOfStudy.getClassGroups().add(classGroup);

        classGroup=classGroupRepository.save(classGroup);

        return convertToClassGroupResponseDto(classGroup);

    }

    @Override
    public List<ClassGroupResponseDto> getAllClassGroups() {
        List<ClassGroup> classGroups=classGroupRepository.findAll();
        return classGroups.stream().map(this::convertToClassGroupResponseDto).collect(Collectors.toList());
    }

    @Override
    public ClassGroupResponseDto getClassGroupById(int id) {
        ClassGroup classGroup=classGroupRepository.findById(id).orElseThrow(
                ()->new ElementNotFoundException("Class Group Not Found")
        );
        return convertToClassGroupResponseDto(classGroup);
    }

    private LevelOfStudyResponseDto convertToLevelOfStudyResponseDto(LevelOfStudy levelos) {
        LevelOfStudyResponseDto dto = new LevelOfStudyResponseDto();
        dto.setId(levelos.getId());
        dto.setName(levelos.getName());
        dto.setReference(levelos.getReference());
        return dto;
    }

    private ClassGroupResponseDto convertToClassGroupResponseDto(ClassGroup classGroup) {
        ClassGroupResponseDto dto = new ClassGroupResponseDto();
        dto.setId(classGroup.getId());
        dto.setName(classGroup.getName());
        dto.setReference(classGroup.getReference());
        dto.setLevelOfStudy(convertToLevelOfStudyResponseDto(classGroup.getLevelOfStudy()));
        return dto;

    }
}
