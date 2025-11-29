package com.university.forum.usermanagement.MemberManagement.Controllers;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.ReportRequest;
import com.university.forum.usermanagement.MemberManagement.Dtos.Response.MemberResponseDto;
import com.university.forum.usermanagement.MemberManagement.Services.MemberService;
import com.university.forum.usermanagement.MemberManagement.Services.StudentService;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usermanagement/member/")
public class MemberController {

    private  final MemberService memberService;
    private  final JwtTokenUtil jwtTokenUtil;

    public MemberController(MemberService memberService, JwtTokenUtil jwtTokenUtil) {
        this.memberService = memberService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("members/me")
    public ResponseEntity<?> getMemberMe(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        MemberResponseDto memberResponseDto=memberService.getMemberMe(memberId);
        response.put("member", memberResponseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("members/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable UUID memberId) {
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        MemberResponseDto memberResponseDto=memberService.getMemberById(memberId);
        response.put("member", memberResponseDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("updateProfileImage")
    public ResponseEntity<?> updateProfileImage(@RequestHeader("Authorization") String authHeader,@RequestParam(value = "profileImage",required = true) MultipartFile[] images) {
        String token = authHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        if (memberId==null) {
            return ResponseEntity.badRequest().body(Map.of("message","memberId must be greater than 0"));
        }
        if (images == null || images.length != 1 || images[0].isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message","Please upload exactly one file."));
        }

        MultipartFile image = images[0];

        String imageUrl = memberService.updateProfileImage(memberId,image);
        return ResponseEntity.ok(Map.of("imageUrl",imageUrl));

    }

    @PostMapping("report")
    public ResponseEntity<?> reportMember(@RequestHeader("Authorization") String authHeader, @RequestBody ReportRequest reportRequest){
        String token = authHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        if (memberId==null) {
            return ResponseEntity.badRequest().body(Map.of("message","memberId must be greater than 0"));
        }

        memberService.reportMember(memberId,reportRequest);

        return ResponseEntity.ok(Map.of("message","Reported successfully!"));

    }
}
