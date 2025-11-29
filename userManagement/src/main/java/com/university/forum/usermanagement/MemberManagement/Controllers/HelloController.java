package com.university.forum.usermanagement.MemberManagement.Controllers;


import com.university.forum.usermanagement.Shared.Dtos.Messages.NotificationMessage;
import com.university.forum.usermanagement.Shared.Services.MemberNotificationProducer;
import com.university.forum.usermanagement.Shared.Services.MessageProducer;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final MessageProducer messageProducer;
    private final MemberNotificationProducer  notificationSender;
    private final JwtTokenUtil jwtTokenUtil;

    public HelloController(final MessageProducer messageProducer, MemberNotificationProducer notificationSender, JwtTokenUtil jwtTokenUtil ) {
        this.messageProducer = messageProducer;
        this.notificationSender = notificationSender;
        this.jwtTokenUtil = jwtTokenUtil;
    }

@GetMapping("/")
public ResponseEntity<String> test() {
        return ResponseEntity.ok("Api gate server is running successfully ♥");
}

    @Operation(summary = "Say Hello", description = "Returns a hello message.")
    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "Helmi") String name) {
        return "Hello, " + name + "!";
    }
    @Operation(summary = "say hi", description = "ll")
    @GetMapping("/hi")
    public String hi(@RequestParam (defaultValue = "monji") String name) {
        return "Hi " + name + "!";
    }

    @Operation(summary = "test the rabbitmq")
    @GetMapping("/test/{message}")
    public String testRabbitmq(@PathVariable String message) {
        messageProducer.sendMessage(message);
        return "message sent : "+message;
    }

    @Operation(summary = "test notif rabbitmq sender")
    @PostMapping("/test/send-notification")
    public ResponseEntity<?> testNotifRabbitmqSender(@RequestHeader("Authorization") String authorizationHeader, @RequestBody NotificationMessage notificationMessage) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);
        Map<String,Object> response=new HashMap<>();
        logger.info("Member Id : {}",memberId);
        if (memberId==null) {
            response.put("message", "memberId must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }

        notificationSender.sendNotification(notificationMessage);
        return ResponseEntity.ok().body(Map.of("Notification",notificationMessage));
    }


}
