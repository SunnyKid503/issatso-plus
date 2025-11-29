package com.university.forum.notificationManagement.Controllers;


import com.university.forum.notificationManagement.Dtos.Requests.TokenRequest;
import com.university.forum.notificationManagement.Services.FcmTokenService;
import com.university.forum.notificationManagement.Services.Impl.NotificationServiceImpl;
import com.university.forum.notificationManagement.Utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications/")
public class FcmTokenController {

    private static final Logger logger = LoggerFactory.getLogger(FcmTokenController.class);

    private final FcmTokenService fcmTokenService;
    private final JwtTokenUtil jwtTokenUtil;

    public FcmTokenController(FcmTokenService fcmTokenService, JwtTokenUtil jwtTokenUtil) {
        this.fcmTokenService = fcmTokenService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Operation(summary = "Register fcm token",description = "Register the fcm token")
    @PostMapping("/register-fcmToken")
    public ResponseEntity<?> registerToken(@RequestHeader("Authorization") String authorizationHeader, @RequestBody TokenRequest tokenRequest) {
        logger.info("Controller authrozationHeader is {} ",authorizationHeader);

        String token = authorizationHeader.replace("Bearer ", "");
        logger.info("Controller token is {} ",token);

        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        logger.info("Controller FCM token register, memberId={} ",memberId);
        fcmTokenService.registerFcmToken(memberId,tokenRequest);
        response.put("message", "Token registered successfully");
        return ResponseEntity.ok(response);
    }

}
