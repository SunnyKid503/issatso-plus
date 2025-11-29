package com.university.forum.usermanagement.AdminManagement.Controllers;


import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.LevelOfStudyRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request.SpecialityRequestDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.LevelOfStudyResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.SpecialityResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Services.LevelOfStudyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usermanagement/admin/levelsOfStudy")
public class LevelOfStudyController {
    private final LevelOfStudyService levelOfStudyService;

    public LevelOfStudyController(LevelOfStudyService levelOfStudyService) {
        this.levelOfStudyService = levelOfStudyService;
    }


    @Operation(description = "create levelOfStudy")
    @PostMapping("/")
    public ResponseEntity<?> createLevelOfStudy(@Valid @RequestBody LevelOfStudyRequestDto levelOfStudyRequestDto) {

        LevelOfStudyResponseDto levelOfStudyResponseDto=levelOfStudyService.createLevelOfStudy(levelOfStudyRequestDto);
        Map<String,Object> response=new HashMap<>();
        response.put("levelOfStudy",levelOfStudyResponseDto);
        response.put("message","LevelOfStudy created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "get all levelOfStudies")
    @GetMapping("/")
    public ResponseEntity<?> getAllLevelOfStudies() {
        List<LevelOfStudyResponseDto> levelOfStudyResponseDtos=levelOfStudyService.getAllLevelOfStudies();
        Map<String,Object> response=new HashMap<>();
        response.put("levelOfStudies",levelOfStudyResponseDtos);
        response.put("message","LevelOfStudies returned successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "get levelOfStudy by id")
    @GetMapping("levelOfStudies/{id}")
    public ResponseEntity<?> getLevelOfStudyById(@PathVariable int id) {
        LevelOfStudyResponseDto levelOfStudyResponseDto=levelOfStudyService.getLevelOfStudyById(id);
        Map<String,Object> response=new HashMap<>();
        response.put("levelOfStudy",levelOfStudyResponseDto);
        response.put("message","LevelOfStudy returned successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "get groups by level id")
    @GetMapping("/{id}/groups")
    public ResponseEntity<?> getGroupsByLevelId(@PathVariable int id) {
        if(id<=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is invalid");
        }
        List<DepartmentResponseDto> groups=levelOfStudyService.getGroupsByLevelId(id);
        Map<String,Object> message=new HashMap<>();
        message.put("groups",groups);
        message.put("message","groups retrieved successfully");
        return ResponseEntity.ok(message);
    }
}
