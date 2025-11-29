package com.university.forum.notificationManagement.RabbitMQListeners;

import com.university.forum.notificationManagement.Dtos.Messages.NewPasswordMessage;
import com.university.forum.notificationManagement.Services.EmailService;
import com.university.forum.notificationManagement.Services.FcmTokenService;
import com.university.forum.notificationManagement.Services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private  final FcmTokenService fcmTokenService;
    private final EmailService emailService;

    public MemberListener(FcmTokenService fcmTokenService, EmailService emailService) {
        this.fcmTokenService = fcmTokenService;
        this.emailService = emailService;
    }

    @RabbitListener(queues = "member.deleteFcmToken.queue")
    public void deleteFcmToken(Map<String,Object> fcmTokenMessage) {
        String fcmToken=(String) fcmTokenMessage.get("fcmToken");
        logger.info("Deleting FCM Token received : {}", fcmToken);
        if(fcmToken!=null && !fcmToken.isBlank()){
            fcmTokenService.deleteFcmToken(fcmToken);
        }
    }

        @RabbitListener(queues = "password.reset.queue")
        public void handlePasswordReset(NewPasswordMessage message) {
            try {
                System.out.println("📥 Received new password message: " + message);

                String subject = "🔐 Your New Password";
                String body = "Hi, your new password is: " + message.getNewPassword();

                emailService.sendPasswordEmailWithHtml(message.getEmail(), subject, body);
            }catch (Exception e) {
                logger.error("❌ Failed to send password email: {}", e.getMessage(), e);
            }

        }

}
