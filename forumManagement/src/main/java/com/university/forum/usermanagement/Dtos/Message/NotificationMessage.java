package com.university.forum.usermanagement.Dtos.Message;

import com.university.forum.usermanagement.Models.Enum.NotificationTopic;
import com.university.forum.usermanagement.Models.Enum.NotificationType;

import java.util.List;
import java.util.UUID;

public class NotificationMessage {

    private String title;
    private String body;
    private NotificationType type;
    private String imageUrl;
    private String link;
    private NotificationTopic notificationTopic;

    private List<UUID> receiversIds;
    public NotificationMessage() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
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

    public NotificationTopic getNotificationTopic() {
        return notificationTopic;
    }

    public void setNotificationTopic(NotificationTopic notificationTopic) {
        this.notificationTopic = notificationTopic;
    }

    public List<UUID> getReceiversIds() {
        return receiversIds;
    }

    public void setReceiversIds(List<UUID> receiversIds) {
        this.receiversIds = receiversIds;
    }

    public NotificationMessage(String title, String body, NotificationType type, String imageUrl, String link, NotificationTopic notificationTopic, List<UUID> receiversIds) {
        this.title = title;
        this.body = body;
        this.type = type;
        this.imageUrl = imageUrl;
        this.link = link;
        this.notificationTopic = notificationTopic;
        this.receiversIds = receiversIds;
    }

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", type=" + type +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", notificationTopic=" + notificationTopic +
                ", receiversIds=" + receiversIds +
                '}';
    }
}
