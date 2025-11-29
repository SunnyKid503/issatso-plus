package com.university.forum.usermanagement.AdminManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.MemberBannedException;
import com.university.forum.usermanagement.AdminManagement.Models.Admin;
import com.university.forum.usermanagement.AdminManagement.Repositories.AdminRepository;
import com.university.forum.usermanagement.AdminManagement.Services.AdminService;
import com.university.forum.usermanagement.MemberManagement.Models.Ban;
import com.university.forum.usermanagement.MemberManagement.Models.Member;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Models.Token;
import com.university.forum.usermanagement.MemberManagement.Repositories.TokenRepository;
import com.university.forum.usermanagement.Shared.Dtos.Request.AuthRequest;
import com.university.forum.usermanagement.Shared.Dtos.Response.AdminAuthResponse;
import com.university.forum.usermanagement.Shared.Dtos.Response.AuthResponse;
import com.university.forum.usermanagement.Shared.Services.PasswordService;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordService passwordService;
    private  final JwtTokenUtil jwtTokenUtil;
    private final TokenRepository tokenRepository;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordService passwordService, JwtTokenUtil jwtTokenUtil, TokenRepository tokenRepository) {
        this.adminRepository = adminRepository;
        this.passwordService = passwordService;

        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenRepository = tokenRepository;
    }


    public AdminAuthResponse login(AuthRequest authRequest) {
        Admin admin = adminRepository.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username " + authRequest.getUsername() + " not found")
        );
        admin.setLast_login_attempt(new Timestamp(System.currentTimeMillis()));
        adminRepository.save(admin);

        if (!passwordService.validatePassword(authRequest.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        String accessToken = jwtTokenUtil.generateAccessToken(admin.getId(),authRequest.getUsername(),
                admin.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        String refreshToken = jwtTokenUtil.generateRefreshToken(admin.getId(),authRequest.getUsername(),
                admin.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));


           Token token = new Token(refreshToken, false, false, admin);
           tokenRepository.save(token);

        Map<String,Object> adminDetails=new HashMap<>();
        adminDetails.put("username",admin.getUsername());
        adminDetails.put("fullname",admin.getFullName());
        AdminAuthResponse adminAuthResponse=new AdminAuthResponse();
        adminAuthResponse.setAdmin(adminDetails);
        adminAuthResponse.setAccessToken(accessToken);
        adminAuthResponse.setRefreshToken(refreshToken);

        return adminAuthResponse;
    }

    @Override
    public AdminAuthResponse refresh(UUID adminId, String refreshToken) {
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new UsernameNotFoundException("Username " + adminId + " not found")
        );


        String username=admin.getUsername();

        Token storedToken=tokenRepository.findByToken(refreshToken).orElseThrow(
                ()-> new IllegalArgumentException("Token not found")
        );


        if (!jwtTokenUtil.validateToken(refreshToken)) {
            tokenRepository.delete(storedToken);
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String tokenType = jwtTokenUtil.getTokenTypeFromToken(refreshToken);
        if (!"REFRESH".equals(tokenType)) {
            tokenRepository.delete(storedToken);
            throw new IllegalArgumentException("Invalid token type. Expected REFRESH token.");
        }

        String newAccessToken = jwtTokenUtil.generateAccessToken(admin.getId(), username,
                admin.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        AdminAuthResponse adminAuthResponse=new AdminAuthResponse();
        adminAuthResponse.setAccessToken(newAccessToken);

        return adminAuthResponse;
    }



}
