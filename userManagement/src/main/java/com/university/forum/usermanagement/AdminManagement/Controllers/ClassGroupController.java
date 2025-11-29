package com.university.forum.usermanagement.AdminManagement.Controllers;


import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.ClassGroupRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.LevelOfStudyRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.ClassGroupResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.LevelOfStudyResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Services.ClassGroupService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usermanagement/admin/classGroup/")
public class ClassGroupController {

    private final ClassGroupService classGroupService;

    public ClassGroupController(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }


    @Operation(description = "create ClassGroup")
    @PostMapping("createClassGroup")
    public ResponseEntity<?> createClassGroup(@Valid @RequestBody ClassGroupRequestDto classGroupRequestDto) {

        ClassGroupResponseDto classGroup=classGroupService.createClassGroup(classGroupRequestDto);
        Map<String,Object> response=new HashMap<>();
        response.put("ClassGroup",classGroup);
        response.put("message","ClassGroup created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "get all classGroups")
    @GetMapping("classGroups")
    public ResponseEntity<?> getAllLevelOfStudies() {
        List<ClassGroupResponseDto> classGroupResponseDtos=classGroupService.getAllClassGroups();
        Map<String,Object> response=new HashMap<>();
        response.put("classGroups",classGroupResponseDtos);
        response.put("message","Class Groups returned successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(description = "get classGroup by id")
    @GetMapping("classGroups/{id}")
    public ResponseEntity<?> getLevelOfStudyById(@PathVariable int id) {
        ClassGroupResponseDto classGroupResponseDto=classGroupService.getClassGroupById(id);
        Map<String,Object> response=new HashMap<>();
        response.put("classGroup",classGroupResponseDto);
        response.put("message","Class Group returned successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
