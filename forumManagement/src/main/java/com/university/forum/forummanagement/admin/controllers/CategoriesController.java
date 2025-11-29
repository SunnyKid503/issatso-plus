package com.university.forum.forummanagement.admin.controllers;

import com.university.forum.forummanagement.admin.dtos.requests.CreateCategoryRequestDto;
import com.university.forum.forummanagement.forums.services.interfaces.CategoryService;
import com.university.forum.forummanagement.shared.exceptions.ElementAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/forums/admin/category")
public class CategoriesController {
    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(description = "creates a category")
    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCategoryRequestDto dto) throws ElementAlreadyExistsException {
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", categoryService.create(dto));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "gets all categories")
    @GetMapping("all")
    public ResponseEntity<?> get() throws ElementAlreadyExistsException {
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", categoryService.get());
        return ResponseEntity.ok(response);
    }
}
