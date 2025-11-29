package com.university.forum.usermanagement.Controllers;

import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.usermanagement.Dtos.Request.ReportRequest;
import com.university.forum.usermanagement.Services.ReportService;
import com.university.forum.usermanagement.Utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/forums/")
public class ReportController {

    private final ReportService reportService;
    private final JwtTokenUtil jwtTokenUtil;

    public ReportController(ReportService reportService, JwtTokenUtil jwtTokenUtil) {
        this.reportService = reportService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("reportPost")
    public ResponseEntity<?> reportPost(@RequestHeader("Authorization") String authHeader, @RequestBody ReportRequest reportRequest) throws ElementNotFoundException {
        String token = authHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        if (memberId==null) {
            return ResponseEntity.badRequest().body(Map.of("message","memberId must be greater than 0"));
        }
        reportService.reportPost(memberId,reportRequest);
        return ResponseEntity.ok(Map.of("message","Post reported successfully!"));
    }

    @PostMapping("reportComment")
    public ResponseEntity<?> reportComment(@RequestHeader("Authorization") String authHeader, @RequestBody ReportRequest reportRequest) throws ElementNotFoundException {
        String token = authHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        if (memberId==null) {
            return ResponseEntity.badRequest().body(Map.of("message","memberId must be greater than 0"));
        }
        reportService.reportComment(memberId,reportRequest);
        return ResponseEntity.ok(Map.of("message","Comment reported successfully!"));
    }
}
