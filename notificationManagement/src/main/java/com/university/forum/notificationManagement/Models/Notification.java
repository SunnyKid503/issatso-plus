package com.university.forum.notificationManagement.Models;

import com.university.forum.notificationManagement.Models.Enums.NotificationTopic;
import com.university.forum.notificationManagement.Models.Enums.NotificationType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "idx_notification_type",columnList = "notification_type"),
        @Index(name="idx_topic",columnList = "topic"),
        @Index(name = "idx_title", columnList = "title")
})

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    private String imageUrl;

    private String link;

    @Enumerated(EnumType.STRING)
    private NotificationTopic topic;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotificationMember> recipients;


    public Notification(Long id, String title, NotificationType notificationType, String body, String imageUrl, String link, Boolean isViewed, NotificationTopic topic, LocalDateTime createdAt, LocalDateTime updatedAt, List<NotificationMember> recipients) {
        this.id = id;
        this.title = title;
        this.notificationType = notificationType;
        this.body = body;
        this.imageUrl = imageUrl;
        this.link = link;
        this.topic = topic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.recipients = recipients;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<NotificationMember> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<NotificationMember> recipients) {
        this.recipients = recipients;
    }

    public Notification() {
    }
}
