package com.university.forum.notificationManagement.Dtos.Requests;

import jakarta.validation.constraints.NotBlank;

public class NotificationRequest {
    private String token;
    @NotBlank(message = "Title must be not null")
    private String title;
    @NotBlank(message = "Body must be not null")
    private String body;

    public NotificationRequest() {
    }

    public NotificationRequest(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
}
