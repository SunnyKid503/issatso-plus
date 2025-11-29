package com.university.forum.notificationManagement.Services;

import com.university.forum.notificationManagement.Dtos.Messages.NotificationMessage;
import com.university.forum.notificationManagement.Dtos.Requests.GetNotificationRequest;
import com.university.forum.notificationManagement.Dtos.Requests.NotificationRequest;
import com.university.forum.notificationManagement.Dtos.Responses.NotificationResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void sendTestNotification(String token, String title, String body);

    void sendTestNotificationToUserById(UUID userId, NotificationRequest request);

    void sendNotification(NotificationMessage message);

    List<NotificationResponse> getNotifications(UUID memberId, GetNotificationRequest notificationRequest);

    NotificationResponse viewNotification(UUID memberId, Long id);

    void deleteNotificationById(UUID memberId, Long id);

    List<NotificationResponse> viewNotificationList(UUID memberId, List<Long> notificationRequest);
}
