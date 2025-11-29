package com.university.forum.notificationManagement.Services;
import com.google.firebase.messaging.*;
import com.university.forum.notificationManagement.Repositories.FcmTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationSendService {

    private final FcmTokenRepository fcmTokenRepository;

    public NotificationSendService(FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }


    public void sendNotification(String token, Long notificationId, String title, String body, String type, String status, String imageUrl, String link, LocalDateTime sentAt) {
        Message message = Message.builder()
                .putData("notificationId", String.valueOf(notificationId))
                .putData("title", title)
                .putData("body", body)
                .putData("type",type)
                .putData("status",status)
                .putData("imageUrl",imageUrl)
                .putData("link",link)
                .putData("sentAt",sentAt.toString())
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            System.out.println("Successfully sent message: " + response);
        } catch (InterruptedException | ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof FirebaseMessagingException) {
                FirebaseMessagingException fme = (FirebaseMessagingException) cause;
                String errorCode = String.valueOf(fme.getErrorCode());
                // If the error indicates an invalid/expired token, delete it.
                if ("messaging/registration-token-not-registered".equals(errorCode)
                        || "messaging/invalid-registration-token".equals(errorCode)
                        || fme.getMessage().contains("Requested entity was not found")) {

                    fcmTokenRepository.deleteByToken(token);
                    System.err.println("FCM token is invalid/expired; token deleted.");
                } else if (fme.getMessage().contains("Error getting access token for service account")) {

                    System.err.println("Connection issue: " + fme.getMessage());
                } else {
                    System.err.println("FCM error: " + fme.getMessage());
                }
            } else {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }

    }

    public void sendTopicNotification(String topic, String title, String body) {
        Message message = Message.builder()
                .putData("title", title)
                .putData("body", body)
                .setTopic(topic)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            System.out.println("Successfully sent message to topic: " + response);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Failed to send message to topic: " + e.getMessage());
        }
    }

    public boolean validateFcmToken(String token) {
        try {
            Message message = Message.builder()
                    .putData("test", "validation")
                    .setToken(token)
                    .build();
            FirebaseMessaging.getInstance().send(message);
            return true;
        } catch (FirebaseMessagingException e) {
            return false;
        }
    }
}