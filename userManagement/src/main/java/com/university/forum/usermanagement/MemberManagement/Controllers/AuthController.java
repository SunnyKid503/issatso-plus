package com.university.forum.usermanagement.MemberManagement.Controllers;

import com.university.forum.usermanagement.MemberManagement.Dtos.Request.RefreshTokenRequest;
import com.university.forum.usermanagement.MemberManagement.Services.AuthenticationService;
import com.university.forum.usermanagement.Shared.Dtos.Request.AuthRequest;
import com.university.forum.usermanagement.Shared.Dtos.Response.AuthResponse;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usermanagement/member/auth/")
public class AuthController {


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationService authService;

    public AuthController(JwtTokenUtil jwtTokenUtil, AuthenticationService authService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authService = authService;
    }


    @Operation(description = "user login")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login( @RequestBody AuthRequest request) {
        System.out.println("loginnnnnnnnnnnnnnnnnnnnnnnnnnn and request is "+request);
        AuthResponse tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }

    @Operation(description = "refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest refreshToken) {

        logger.info("Refresh token request received: {}", refreshToken);

        if (refreshToken.getRefreshToken()==null || refreshToken.getRefreshToken().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Refresh token needed"));
        }
        UUID memberId= jwtTokenUtil.getUserIdFromToken(refreshToken.getRefreshToken());
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        AuthResponse authResponse=authService.refresh(memberId,refreshToken.getRefreshToken());
        return ResponseEntity.ok(authResponse);
    }

    @Operation(description = "logout")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String auth, @RequestBody RefreshTokenRequest refreshTokenRequest) {
       String token=auth.replace("Bearer ","");
       UUID memberId= jwtTokenUtil.getUserIdFromToken(token);
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }

        logger.info("logout token is : {}, memberId {}",refreshTokenRequest.getRefreshToken(),memberId);
        authService.logout(memberId,refreshTokenRequest);
        return ResponseEntity.ok(Map.of("message","User logged out successfully"));
    }


    /*
    hadia professor access token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYWRpYUBnbWFpbC5jb20iLCJtZW1iZXJJZCI6IjhjOTA2MjIyLWRiYTgtNGIwYy1iODQ3LTNlNWE5ZTg0MzM4YyIsInJvbGVzIjpbIlJPTEVfQURNSU5JU1RSQVRPUiIsIlJPTEVfUFJPRkVTU09SIl0sInRva2VuVHlwZSI6IkFDQ0VTUyIsImlhdCI6MTc0MzU1MTE5NiwiZXhwIjoxNzQ2NTYwNzk2fQ.-sZ2IVk_Ay3KiJmYy6YAmhS_Wv2l4avPgO_1dcOBjz8
    helmi student access token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxtaUBnbWFpbC5jb20iLCJtZW1iZXJJZCI6ImM5YjNhOTAzLThkMjItNDg2Zi1iNTFjLTllODI3OTkzNGQ0MyIsInJvbGVzIjpbIlJPTEVfU1RVREVOVCJdLCJ0b2tlblR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3NDMyNTk2NDAsImV4cCI6MTc0NjI2OTI0MH0._c3v2a9MkmR8pTVA-DapS6l1wW2DoMuICLoeeLb5G4Y

    helmi student refresh token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxtaUBnbWFpbC5jb20iLCJtZW1iZXJJZCI6ImM5YjNhOTAzLThkMjItNDg2Zi1iNTFjLTllODI3OTkzNGQ0MyIsInJvbGVzIjpbIlJPTEVfU1RVREVOVCJdLCJ0b2tlblR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNzQzMzQwMzcxLCJleHAiOjE3NDYzNDk5NzF9.5P9S9u3r4oOBj2GasfIrTU5U88yvq1Hx2FcXyGulmeU

    helmi2 ADMINISTRATOR_ROLE access token:  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxtaTJAZ21haWwuY29tIiwibWVtYmVySWQiOiI3OTAwYzg2YS00OWE2LTQzY2ItOTVkOC1lNjRiYTEzZGNkMTUiLCJyb2xlcyI6WyJST0xFX0FETUlOSVNUUkFUT1IiXSwidG9rZW5UeXBlIjoiQUNDRVNTIiwiaWF0IjoxNzQzODk2MDE0LCJleHAiOjE3NDY5MDU2MTR9.Vz4LWNyy4Lx6H2zvmRQ2EJ1GIBcasDY4Xxj6QDfla4M
    helmi2 ADMINISTRATOR_ROLE refresh token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxtaTJAZ21haWwuY29tIiwibWVtYmVySWQiOiI3OTAwYzg2YS00OWE2LTQzY2ItOTVkOC1lNjRiYTEzZGNkMTUiLCJyb2xlcyI6WyJST0xFX0FETUlOSVNUUkFUT1IiXSwidG9rZW5UeXBlIjoiUkVGUkVTSCIsImlhdCI6MTc0MzYzMjc1NiwiZXhwIjoxNzQ2NjQyMzU2fQ.0_zuWrpnFewg7mtQ0K7FCKcb7nhhKhJCu0YK2V98XTY
         */

}
