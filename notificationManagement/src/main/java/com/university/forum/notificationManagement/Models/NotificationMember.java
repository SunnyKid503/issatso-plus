package com.university.forum.notificationManagement.Models;


import com.university.forum.notificationManagement.Models.Enums.DeliveryStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_member_id", columnList = "member_id"),
        @Index(name = "idx_delivery_status", columnList = "delivery_status")
})
public class NotificationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus;


    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;


    private LocalDateTime viewedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public NotificationMember(Long id, Notification notification, Member member, DeliveryStatus deliveryStatus, LocalDateTime deliveredAt, LocalDateTime viewedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.notification = notification;
        this.member = member;
        this.deliveryStatus = deliveryStatus;
        this.deliveredAt = deliveredAt;
        this.viewedAt = viewedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NotificationMember(Member member, Notification notification, DeliveryStatus deliveryStatus, LocalDateTime deliveredAt) {
        this.member = member;
        this.notification = notification;
        this.deliveryStatus = deliveryStatus;
        this.deliveredAt = deliveredAt;
        this.viewedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
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

    public NotificationMember() {
    }
}