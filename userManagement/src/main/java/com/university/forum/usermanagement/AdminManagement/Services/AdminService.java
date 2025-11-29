package com.university.forum.usermanagement.AdminManagement.Services;

import com.university.forum.usermanagement.Shared.Dtos.Request.AuthRequest;
import com.university.forum.usermanagement.Shared.Dtos.Response.AdminAuthResponse;
import com.university.forum.usermanagement.Shared.Dtos.Response.AuthResponse;

import java.util.UUID;

public interface AdminService {
    AdminAuthResponse login(AuthRequest request);

    AdminAuthResponse refresh(UUID userId, String refreshToken);
}
