package com.university.forum.forummanagement.forums.controllers;

import com.university.forum.forummanagement.forums.dtos.requests.CreateAnnouncementRequestDto;
import com.university.forum.forummanagement.forums.dtos.requests.CreatePostRequestDto;
import com.university.forum.forummanagement.forums.services.interfaces.AnnouncementService;
import com.university.forum.forummanagement.shared.abstracts.ControllerBase;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forums/announcement")
public class AnnouncementController extends ControllerBase {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Operation(description = "Get all announcements")
    @GetMapping("all")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authHeader) throws ElementNotFoundException, IllegalOperationException {
//        Map<String,Object> response = new HashMap<>();
//        response.put("data", announcementService.get());
//        return ResponseEntity.ok(response);
        Map<String,Object> response = new HashMap<>();
        UUID userId = extractUserId(authHeader);
        Set<String> roles = extractRoles(authHeader);
        response.put("data", announcementService.get(userId, roles));
        return ResponseEntity.ok(response);
    }

    //@Operation(description = "Get all announcements")
    //@GetMapping("you")
    public ResponseEntity<?> getForUser(@RequestHeader("Authorization") String authHeader) throws ElementNotFoundException, IllegalOperationException {
        Map<String,Object> response = new HashMap<>();
        UUID userId = extractUserId(authHeader);
        Set<String> roles = extractRoles(authHeader);
        response.put("data", announcementService.get(userId, roles));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Create an announcement")
    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestHeader("Authorization") String authHeader,
            @Valid @ModelAttribute CreateAnnouncementRequestDto dto) throws ElementNotFoundException, FileUploadException {
        Map<String,Object> response = new HashMap<>();

        if(dto == null) {
            response.put("errors", "request body should not be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        UUID userId = extractUserId(authHeader);
        response.put("data", announcementService.create(userId, dto));
        return ResponseEntity.ok(response);
    }



}