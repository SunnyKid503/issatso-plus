package com.university.forum.usermanagement.Shared.Dtos.Response;

import java.util.HashMap;
import java.util.Map;

public class AdminAuthResponse {
    private String accessToken;
    private String refreshToken;
    private Map<String,Object> admin;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Map<String, Object> getAdmin() {
        return admin;
    }

    public void setAdmin(Map<String, Object> admin) {
        this.admin = admin;
    }

    public AdminAuthResponse(String accessToken, String refreshToken, Map<String, Object> admin) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.admin = admin;
    }

    public AdminAuthResponse() {
    }

    @Override
    public String toString() {
        return "AdminAuthResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", admin=" + admin +
                '}';
    }
}
