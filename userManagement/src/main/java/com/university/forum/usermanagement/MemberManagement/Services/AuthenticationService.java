package com.university.forum.usermanagement.MemberManagement.Services;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.RefreshTokenRequest;
import com.university.forum.usermanagement.Shared.Dtos.Request.AuthRequest;
import com.university.forum.usermanagement.Shared.Dtos.Response.AuthResponse;

import java.util.Map;
import java.util.UUID;

public interface AuthenticationService {
    AuthResponse login(AuthRequest request);

    AuthResponse refresh(UUID memberId,String refreshToken);

    void logout(UUID memberId, RefreshTokenRequest refreshTokenRequest);
}
