package com.university.forum.notificationManagement.Controllers;

import com.university.forum.notificationManagement.Services.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications/")
public class EmailNotificationController {

    private final EmailService emailService;

    public EmailNotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("send-email")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String content) {
        emailService.sendEmailWithHtml(toEmail, subject,content);
        return "Email sent to " + toEmail;
    }
}
