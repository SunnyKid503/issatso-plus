package com.university.forum.usermanagement.AdminManagement.Controllers;


import com.university.forum.usermanagement.AdminManagement.Dtos.BulkUploadResponse;
import com.university.forum.usermanagement.AdminManagement.Services.StudentBulkService;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.StudentUpdateRequestDto;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.StudentResponseDto;
import com.university.forum.usermanagement.MemberManagement.Services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usermanagement/admin/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentBulkService studentBulkService;

    public StudentController(StudentService studentService, StudentBulkService studentBulkService) {
        this.studentService = studentService;
        this.studentBulkService = studentBulkService;
    }



    @Operation(summary = "Create Student", description = "Create a new student")
    @PostMapping("/")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequestDto studentRequestDto) {

       studentService.createStudent(studentRequestDto);
        Map<String,Object> response=new HashMap<>();
        response.put("message","Student created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public  ResponseEntity<?> getAllStudents() {
        Map<String,Object> response=new HashMap<>();
        List<StudentResponseDto> responseDtos=studentService.getAll();
        response.put("students",responseDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable UUID id) {
        Map<String,Object> response=new HashMap<>();
        if(id==null){
            response.put("message","Student id cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        StudentResponseDto studentResponseDto=studentService.getStudentById(id);
        response.put("message","Student returned successfully");
        response.put("student",studentResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentUpdateRequestDto studentRequestDto) {
        Map<String,Object> response=new HashMap<>();
        if(id==null){
            response.put("message","Student id cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        studentService.updateStudent(id,studentRequestDto);
        response.put("message","Student updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping(value = "/bulk-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BulkUploadResponse> uploadStudents(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new BulkUploadResponse(0,
                            java.util.Collections.singletonList("File is empty"), 0, 1)
            );
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isExcelFile(fileExtension)) {
            return ResponseEntity.badRequest().body(
                    new BulkUploadResponse(0,
                            java.util.Collections.singletonList("Only Excel files (.xlsx, .xls) are supported"), 0, 1)
            );
        }

        BulkUploadResponse response = studentBulkService.processExcel(file);

        HttpStatus status = response.getErrorCount() > 0 && response.getSuccessCount() == 0
                ? HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isExcelFile(String extension) {
        return extension.equals("xlsx") || extension.equals("xls");
    }

}

