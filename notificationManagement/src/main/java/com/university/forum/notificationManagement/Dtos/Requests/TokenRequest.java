package com.university.forum.notificationManagement.Dtos.Requests;

import com.university.forum.notificationManagement.Models.Enums.DeviceType;
import jakarta.validation.constraints.NotBlank;

public class TokenRequest {

    @NotBlank(message = "Fcm token is required")
    private String fcmToken;

    @NotBlank(message="Device type is required")
    private DeviceType deviceType;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public TokenRequest() {
    }

    public TokenRequest(String fcmToken, DeviceType deviceType) {
        this.fcmToken = fcmToken;
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "TokenRequest{" +
                "fcmToken='" + fcmToken + '\'' +
                ", deviceType=" + deviceType +
                '}';
    }
}
