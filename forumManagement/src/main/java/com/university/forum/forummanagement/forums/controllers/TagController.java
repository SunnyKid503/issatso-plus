package com.university.forum.forummanagement.forums.controllers;

import com.university.forum.forummanagement.forums.services.interfaces.TaggableObjectService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/forums/tag")
public class TagController {
    private final TaggableObjectService taggableObjectService;

    public TagController(TaggableObjectService taggableObjectService)
    {
        this.taggableObjectService = taggableObjectService;
    }

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", taggableObjectService.getAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getByName(String name)
    {
        HashMap<String, Object> response = new HashMap<>();
        if(name.isEmpty()){
            response.put("data", taggableObjectService.getAll());
            return ResponseEntity.ok(response);
        }
        response.put("data", taggableObjectService.getByName(name));
        return ResponseEntity.ok(response);
    }

}
