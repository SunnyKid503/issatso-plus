package com.university.forum.notificationManagement.Services;

import com.university.forum.notificationManagement.Dtos.Requests.TokenRequest;

import java.util.UUID;

public interface FcmTokenService {
    void registerFcmToken(UUID memberId, TokenRequest tokenRequest);

    void deleteFcmToken(String fcmToken);
}
