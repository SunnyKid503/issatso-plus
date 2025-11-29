package com.university.forum.notificationManagement.Dtos.Requests;


import java.util.List;

public class GetNotificationRequest {
    private Long lastNotificationId;
    private int numberOfNotifications=10;
    private List<Long> notificationIds;

    public List<Long> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }

    public GetNotificationRequest(Long lastNotificationId, int numberOfNotifications, List<Long> notificationIds) {
        this.lastNotificationId = lastNotificationId;
        this.numberOfNotifications = numberOfNotifications;
        this.notificationIds = notificationIds;
    }

    public Long getLastNotificationId() {
        return lastNotificationId;
    }

    public void setLastNotificationId(Long lastNotificationId) {
        this.lastNotificationId = lastNotificationId;
    }

    public int getNumberOfNotifications() {
        return numberOfNotifications;
    }

    public void setNumberOfNotifications(int numberOfNotifications) {
        this.numberOfNotifications = numberOfNotifications;
    }

    public GetNotificationRequest() {
    }
}
