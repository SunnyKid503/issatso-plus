package com.university.forum.forummanagement.forums.controllers;

import com.university.forum.forummanagement.forums.dtos.requests.CreatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.requests.PostInteractionRequestDto;
import com.university.forum.forummanagement.forums.services.interfaces.PostInteractionService;
import com.university.forum.forummanagement.shared.abstracts.ControllerBase;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forums/interaction")
public class PostInteractionController extends ControllerBase {
    private   PostInteractionService postInteractionService ;

    public PostInteractionController(PostInteractionService postInteractionService) {
        this.postInteractionService = postInteractionService;
    }

    @Operation(description = "UpVote a post")
    @PostMapping("upVote")
    public ResponseEntity<?> upVote(@RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostInteractionRequestDto dto) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        if(dto == null) {
            response.put("errors", "request body should not be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("data", postInteractionService.upVote(userId, dto));
        return ResponseEntity.ok(response);
    }
    @Operation(description = "DownVote a post")
    @PostMapping("downVote")
    public ResponseEntity<?> downVote(@RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostInteractionRequestDto dto) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        if(dto == null) {
            response.put("errors", "request body should not be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("data", postInteractionService.downVote(userId, dto));
        return ResponseEntity.ok(response);
    }
    @Operation(description = "Save a post")
    @PostMapping("save")
    public ResponseEntity<?> save(@RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostInteractionRequestDto dto) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        if(dto == null) {
            response.put("errors", "request body should not be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("data", postInteractionService.save(userId, dto));
        return ResponseEntity.ok(response);
    }
    @Operation(description = "see a post")
    @PostMapping("seen")
    public ResponseEntity<?> seen (@RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostInteractionRequestDto dto) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        if(dto == null) {
            response.put("errors", "request body should not be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("data", postInteractionService.seen(userId, dto));
        return ResponseEntity.ok(response);
    }
}
