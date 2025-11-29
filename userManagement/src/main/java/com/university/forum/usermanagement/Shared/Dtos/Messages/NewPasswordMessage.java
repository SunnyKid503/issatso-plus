package com.university.forum.usermanagement.Shared.Dtos.Messages;

import java.util.UUID;

public class NewPasswordMessage {
    private UUID userId;
    private String email;
    private String newPassword;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public NewPasswordMessage(UUID userId, String email, String newPassword) {
        this.userId = userId;
        this.email = email;
        this.newPassword = newPassword;
    }

    public NewPasswordMessage() {
    }

    @Override
    public String toString() {
        return "NewPasswordMessage{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
