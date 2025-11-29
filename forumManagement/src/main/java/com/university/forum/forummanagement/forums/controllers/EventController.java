package com.university.forum.forummanagement.forums.controllers;

import com.university.forum.forummanagement.forums.dtos.requests.CreateEventRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.EventResponseDto;
import com.university.forum.forummanagement.forums.services.interfaces.EventService;
import com.university.forum.forummanagement.shared.abstracts.ControllerBase;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forums/event")
public class EventController extends ControllerBase {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(description = "Get events")
    @GetMapping("all")
    public ResponseEntity<?> getFeed()
    {
        Map<String,Object> response = new HashMap<>();
        response.put("data", eventService.get());
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Creates a new event")
    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
                                    @Valid @ModelAttribute CreateEventRequestDto dto) throws ElementNotFoundException, FileUploadException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        response.put("data", eventService.create(userId, dto));
        return ResponseEntity.ok(response);
    }

}
