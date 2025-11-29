package com.university.forum.notificationManagement.Controllers;
import com.university.forum.notificationManagement.Dtos.Requests.GetNotificationRequest;
import com.university.forum.notificationManagement.Dtos.Requests.NotificationRequest;
import com.university.forum.notificationManagement.Dtos.Responses.NotificationResponse;
import com.university.forum.notificationManagement.Services.NotificationService;
import com.university.forum.notificationManagement.Utils.JwtTokenUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications/")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(FcmTokenController.class);
    private final NotificationService notificationService;
    private final JwtTokenUtil jwtTokenUtil;

    public NotificationController(NotificationService notificationService, JwtTokenUtil jwtTokenUtil) {
        this.notificationService = notificationService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewNotification(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        logger.info("View memberId {}",memberId);
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        NotificationResponse notificationResponse=notificationService.viewNotification(memberId,id);
        response.put("notification",notificationResponse);
        return ResponseEntity.ok(response);

    }


    @PostMapping("/view")
    public ResponseEntity<?> viewNotificationList(@RequestHeader("Authorization") String authorizationHeader, @RequestBody GetNotificationRequest notificationRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        logger.info("View memberId {}",memberId);
        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        if (notificationRequest.getNotificationIds()==null || notificationRequest.getNotificationIds().isEmpty()|| (notificationRequest.getNotificationIds()).get(0) ==0) {
            response.put("message", "notificationIds must not be empty or contains only 0");
            return ResponseEntity.badRequest().body(response);
        }
        List<NotificationResponse> notificationResponse=notificationService.viewNotificationList(memberId,notificationRequest.getNotificationIds());
        response.put("notification",notificationResponse);
        return ResponseEntity.ok(response);

    }



    @PostMapping("/notifications")
    public ResponseEntity<?> getNotifications(@RequestHeader("Authorization") String authorizationHeader,@RequestBody GetNotificationRequest notificationRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        logger.info("getNotifications memberId {}",memberId);

        Map<String,Object> response=new HashMap<>();
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
        List<NotificationResponse> notifications=notificationService.getNotifications(memberId,notificationRequest);
        response.put("message","Notifications retrieved successfully");
        response.put("notifications",notifications);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        logger.info("deleteNotification memberId {}", memberId);

        Map<String, Object> response = new HashMap<>();
        if (memberId == null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }
       if(id<0){
           response.put("message", "notification id must be greater than 0");
            return ResponseEntity.badRequest().body(response);
       }
       notificationService.deleteNotificationById(memberId,id);
       response.put("message","Notification deleted successfully");
       return ResponseEntity.ok(response);
    }

        @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendTestNotification(request.getToken(), request.getTitle(), request.getBody());
        return ResponseEntity.ok("Notification sent");
    }

    @PostMapping("/send-to-user/{userId}")
    public ResponseEntity<?> sendToUser(@PathVariable UUID userId, @RequestBody NotificationRequest request) {
           notificationService.sendTestNotificationToUserById(userId,request);
       return ResponseEntity.ok("Notifications sent to user " + userId);
    }



    // ToDo:  delete every fcm token expired when the send notification returned an error  (DONE !)

}