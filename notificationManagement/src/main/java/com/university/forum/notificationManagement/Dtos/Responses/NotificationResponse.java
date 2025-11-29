package com.university.forum.notificationManagement.Dtos.Responses;


import com.university.forum.notificationManagement.Models.Enums.DeliveryStatus;
import com.university.forum.notificationManagement.Models.Enums.NotificationTopic;
import com.university.forum.notificationManagement.Models.Enums.NotificationType;

import java.time.LocalDateTime;


public class NotificationResponse {

    private Long id;
    private String title;
    private NotificationType notificationType;
    private String body;
    private String imageUrl;
    private String link;
    private NotificationTopic topic;
    private LocalDateTime sentAt;
    private LocalDateTime viewedAt;
    private DeliveryStatus deliveryStatus;

    public NotificationResponse(Long id, String title, NotificationType notificationType, String body, String imageUrl, String link, NotificationTopic topic, LocalDateTime sentAt, LocalDateTime viewedAt, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.title = title;
        this.notificationType = notificationType;
        this.body = body;
        this.imageUrl = imageUrl;
        this.link = link;
        this.topic = topic;
        this.sentAt = sentAt;
        this.viewedAt = viewedAt;
        this.deliveryStatus = deliveryStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public NotificationTopic getTopic() {
        return topic;
    }

    public void setTopic(NotificationTopic topic) {
        this.topic = topic;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public NotificationResponse() {
    }
}
