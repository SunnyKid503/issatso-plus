package com.university.forum.forummanagement.forums.controllers;

import com.university.forum.forummanagement.forums.dtos.requests.CreatePostRequestDto;
import com.university.forum.forummanagement.forums.dtos.requests.UpdatePostRequestDto;
import com.university.forum.forummanagement.forums.services.interfaces.PostService;
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
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/forums/post")
public class PostController extends ControllerBase {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    @Operation(description = "Get a generalized feed")
    @GetMapping("feed")
    public ResponseEntity<?> getFeed()
    {
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.feed());
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get for you feed")
    @GetMapping("fyp")
    public ResponseEntity<?> getFyp(@RequestHeader("Authorization") String authHeader) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.fyp(userId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get for you feed")
    @GetMapping("user")
    public ResponseEntity<?> getForUser(@RequestHeader("Authorization") String authHeader) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.postedBy(userId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Create a post")
    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
            @Valid @ModelAttribute CreatePostRequestDto dto) throws ElementNotFoundException, FileUploadException {
        Map<String,Object> response = new HashMap<>();
        if(dto == null) {
            response.put("errors", "request body should not be empty.");
            return ResponseEntity.badRequest().body(response);
        }
        UUID userId = extractUserId(authHeader);
        response.put("data", postService.create(userId, dto));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get saved posts for a given user")
    @GetMapping("saved")
    public ResponseEntity<?> getSaved(@RequestHeader("Authorization") String authHeader) throws ElementNotFoundException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.saved(userId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get saved post by id")
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable int postId) throws ElementNotFoundException {
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.getPostById(postId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update a post")
    @PutMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authHeader,
            @Valid @ModelAttribute UpdatePostRequestDto dto) throws ElementNotFoundException, IllegalOperationException, FileUploadException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.update(userId, dto));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get saved posts for a given user")
    @DeleteMapping("delete/{postId}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String authHeader,
                                    @PathVariable int postId) throws ElementNotFoundException, IllegalOperationException {
        UUID userId = extractUserId(authHeader);
        Map<String,Object> response = new HashMap<>();
        response.put("data", postService.delete(userId, postId));
        return ResponseEntity.ok(response);
    }



}
