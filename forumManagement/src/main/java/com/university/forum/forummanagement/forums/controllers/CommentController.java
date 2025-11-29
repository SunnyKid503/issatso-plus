package com.university.forum.forummanagement.forums.controllers;

import com.university.forum.forummanagement.forums.dtos.requests.CreateCommentRequestDto;
import com.university.forum.forummanagement.forums.services.interfaces.CommentService;
import com.university.forum.forummanagement.shared.abstracts.ControllerBase;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forums/comment")
public class CommentController extends ControllerBase {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(description = "Get comments for a given post")
    @GetMapping("post/{postId}")
    public ResponseEntity<?> getForPost(@PathVariable int postId)
    {
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", commentService.getForPost(postId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get comments for a given user")
    @GetMapping("user/{userId}")
    public ResponseEntity<?> getForUser(@RequestHeader("Authorization") String authHeader)
    {
        UUID userId = extractUserId(authHeader);
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", commentService.getForUser(userId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get sub-comments for a given comment")
    @GetMapping("comment/{commentId}")
    public ResponseEntity<?> getForComment(@PathVariable int commentId)
    {
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", commentService.getForComment(commentId));
        return ResponseEntity.ok(response);
    }

    @Operation(description = "creates a comment")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody CreateCommentRequestDto dto) throws ElementNotFoundException, IllegalOperationException {
        UUID userId = extractUserId(authHeader);
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", commentService.create(userId, dto));
        return ResponseEntity.ok(response);
    }

}
