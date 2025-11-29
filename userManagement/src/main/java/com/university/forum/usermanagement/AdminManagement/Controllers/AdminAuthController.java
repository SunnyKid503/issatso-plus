package com.university.forum.usermanagement.AdminManagement.Controllers;

import com.university.forum.usermanagement.AdminManagement.Services.AdminService;
import com.university.forum.usermanagement.MemberManagement.Controllers.AuthController;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.RefreshTokenRequest;
import com.university.forum.usermanagement.Shared.Dtos.Request.AuthRequest;
import com.university.forum.usermanagement.Shared.Dtos.Response.AdminAuthResponse;
import com.university.forum.usermanagement.Shared.Dtos.Response.AuthResponse;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usermanagement/admin/auth/")
public class AdminAuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AdminService adminService;
    private final JwtTokenUtil jwtTokenUtil;

    public AdminAuthController(AdminService adminService, JwtTokenUtil jwtTokenUtil) {
        this.adminService = adminService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Operation(description = "Admin login")
    @PostMapping("/login")
    public ResponseEntity<AdminAuthResponse> login(@RequestBody AuthRequest request) {
        System.out.println("Adminn  loginnnnnnn and request is "+request);
        AdminAuthResponse tokens = adminService.login(request);
        logger.info("Login response : "+tokens);
        return ResponseEntity.ok(tokens);
    }


    @Operation(description = "Admin refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest refreshToken) {

        logger.info("Admin Refresh token request received: {}", refreshToken);

        if (refreshToken.getRefreshToken()==null || refreshToken.getRefreshToken().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Refresh token needed"));
        }
        UUID userId= jwtTokenUtil.getUserIdFromToken(refreshToken.getRefreshToken());
        Map<String,Object> response=new HashMap<>();
        if (userId==null) {
            response.put("message", "Admin Id must be not null");
            return ResponseEntity.badRequest().body(response);
        }
        AdminAuthResponse authResponse=adminService.refresh(userId,refreshToken.getRefreshToken());
        logger.info("Refresh response is {}",authResponse);
        return ResponseEntity.ok(authResponse);
    }
}
