package com.university.forum.usermanagement.MemberManagement.Services.Impl;

import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.AccessMemberDeniedException;
import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.ElementNotFoundException;
import com.university.forum.usermanagement.AdminManagement.ExceptionHandler.MemberBannedException;
import com.university.forum.usermanagement.MemberManagement.Dtos.Request.RefreshTokenRequest;
import com.university.forum.usermanagement.MemberManagement.Models.Ban;
import com.university.forum.usermanagement.MemberManagement.Models.Member;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Models.Token;
import com.university.forum.usermanagement.MemberManagement.Repositories.BanRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.MemberRepository;
import com.university.forum.usermanagement.MemberManagement.Repositories.TokenRepository;
import com.university.forum.usermanagement.MemberManagement.Services.AuthenticationService;
import com.university.forum.usermanagement.MemberManagement.Services.BanService;
import com.university.forum.usermanagement.Shared.Dtos.Request.AuthRequest;
import com.university.forum.usermanagement.Shared.Dtos.Response.AuthResponse;
import com.university.forum.usermanagement.Shared.Services.MemberNotificationProducer;
import com.university.forum.usermanagement.Shared.Services.PasswordService;
import com.university.forum.usermanagement.Shared.Utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordService passwordService;
    private final MemberNotificationProducer memberNotificationProducer;
    private final BanRepository banRepository;

    public AuthenticationServiceImpl(MemberRepository memberRepository, TokenRepository tokenRepository, JwtTokenUtil jwtTokenUtil, PasswordService passwordService, MemberNotificationProducer memberNotificationProducer, BanRepository banRepository) {
        this.memberRepository = memberRepository;
        this.tokenRepository = tokenRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordService = passwordService;
        this.memberNotificationProducer = memberNotificationProducer;
        this.banRepository = banRepository;
    }

    public AuthResponse login(AuthRequest authRequest) {
        Member member = memberRepository.findByAddressEmail(authRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username " + authRequest.getUsername() + " not found")
        );
        member.setLast_login_attempt(new Timestamp(System.currentTimeMillis()));
        memberRepository.save(member);

        if (isUserBanned(member)) {
            Ban activeBan = getActiveBan(member);
            throw new MemberBannedException(
                    "Account suspended. Reason: " + activeBan.getReason() +
                            (activeBan.getEndDate() != null ?
                                    " Until: " + activeBan.getEndDate() : "")
            );
        }
        logger.info("User passwords : {} and  the hashed one {}", authRequest.getPassword(),member.getPassword());
        if (!passwordService.validatePassword(authRequest.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        String accessToken = jwtTokenUtil.generateAccessToken(member.getId(),authRequest.getUsername(),
                member.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        String refreshToken = jwtTokenUtil.generateRefreshToken(member.getId(),authRequest.getUsername(),
                member.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));


        Token token = new Token(refreshToken, false, false, member);
        tokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(UUID memberId,String refreshToken) {
        Member member=memberRepository.findById(memberId).orElseThrow(
                ()-> new UsernameNotFoundException("Username " + memberId + " not found")
        );


        if (isUserBanned(member)) {
            Ban activeBan = getActiveBan(member);
            logger.info("Refresh is banned ? : {}", member.getAddressEmail());
            throw new MemberBannedException(
                    "Account suspended. Reason: " + activeBan.getReason() +
                            (activeBan.getEndDate() != null ?
                                    " Until: " + activeBan.getEndDate() : "")
            );
        }
        String username=member.getAddressEmail();
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

        String newAccessToken = jwtTokenUtil.generateAccessToken(member.getId(), username,
                member.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return new AuthResponse(newAccessToken, null);
    }

    @Override
    public void logout(UUID memberId, RefreshTokenRequest refreshTokenRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new UsernameNotFoundException("MemberId " + memberId + " not found")
        );

        if(refreshTokenRequest.getRefreshToken()!=null && !refreshTokenRequest.getRefreshToken().isBlank()) {

            if(!jwtTokenUtil.validateToken(refreshTokenRequest.getRefreshToken())) {
                throw new IllegalArgumentException("Invalid refresh token");
            }
            String tokenType = jwtTokenUtil.getTokenTypeFromToken(refreshTokenRequest.getRefreshToken());
            if(!"REFRESH".equals(tokenType)) {
                throw new IllegalArgumentException("Invalid token type. Expected REFRESH token.");
            }
            Token token=tokenRepository.findByTokenAndUser(refreshTokenRequest.getRefreshToken(),member).orElse(null);
            if (token!=null){
                tokenRepository.delete(token);
                logger.info("Token delete successfully {}, for user {}", refreshTokenRequest.getRefreshToken(), memberId);
            }
        }
        if(refreshTokenRequest.getFcmToken()!=null && !refreshTokenRequest.getFcmToken().isBlank()) {
            memberNotificationProducer.sendDeleteFcmToken(refreshTokenRequest.getFcmToken());
          }

    }


    private boolean isUserBanned(Member member) {
        return banRepository.existsByBannedUserAndIsActiveTrue(member);
    }
    private Ban getActiveBan(Member member) {
        return banRepository.findByBannedUserAndIsActiveTrue(member)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active ban found"));
    }

}
