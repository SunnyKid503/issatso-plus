package com.university.forum.usermanagement.AdminManagement.Controllers;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ProfessorRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ProfessorUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.ProfessorResponseDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.StudentResponseDto;
import com.university.forum.usermanagement.MemberManagement.Services.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usermanagement/admin/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @Operation(summary = "Create Professor", description = "Create a new professor")
    @PostMapping("/")
    public ResponseEntity<?> createProfessor(@Valid @RequestBody ProfessorRequestDto professorRequestDto ) {

        ProfessorResponseDto professorResponseDto=professorService.createProfessor(professorRequestDto);
        Map<String,Object> response=new HashMap<>();
        response.put("professor",professorResponseDto);
        response.put("message","Professor created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/")
    public  ResponseEntity<?> getAllProfessors() {
        Map<String,Object> response=new HashMap<>();
        List<ProfessorResponseDto> responseDtos=professorService.getAll();
        response.put("professors",responseDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @Valid @RequestBody ProfessorUpdateRequestDto professorDto) {
        Map<String,Object> response=new HashMap<>();
        if(id==null){
            response.put("message","Professor id cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        professorService.updateStudent(id,professorDto);
        response.put("message","Professor updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
