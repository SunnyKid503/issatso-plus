package com.university.forum.usermanagement.MemberManagement.Dtos.Request;


public class RefreshTokenRequest {
    private String refreshToken;
    private String fcmToken;

    public RefreshTokenRequest(String refreshToken, String fcmToken) {
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
    }

    public RefreshTokenRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
                "refreshToken='" + refreshToken + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                '}';
    }
}
