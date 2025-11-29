package com.university.forum.notificationManagement.Services.Impl;

import com.university.forum.notificationManagement.Dtos.Requests.TokenRequest;
import com.university.forum.notificationManagement.Models.FcmToken;
import com.university.forum.notificationManagement.Models.Member;
import com.university.forum.notificationManagement.Repositories.FcmTokenRepository;
import com.university.forum.notificationManagement.Repositories.MemberRepository;
import com.university.forum.notificationManagement.Services.FcmTokenService;
import com.university.forum.notificationManagement.Services.NotificationSendService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FcmTokenServiceImpl implements FcmTokenService {

    private static final Logger logger = LoggerFactory.getLogger(FcmTokenServiceImpl.class);

    private final FcmTokenRepository fcmTokenRepository;
    private final MemberRepository memberRepository;
    private final NotificationSendService notificationSendService;

    public FcmTokenServiceImpl(FcmTokenRepository fcmTokenRepository, MemberRepository memberRepository, NotificationSendService notificationSendService) {
        this.fcmTokenRepository = fcmTokenRepository;
        this.memberRepository = memberRepository;
        this.notificationSendService = notificationSendService;
    }


    @Override
    @Transactional
    public void registerFcmToken(UUID memberId, TokenRequest tokenRequest) {
        logger.info("FCM token register, memberId={} ",memberId);
        Member member=memberRepository.findById(memberId).orElse(null);
        if(member==null) {
            FcmToken oldToken=fcmTokenRepository.findByToken(tokenRequest.getFcmToken());
            if(oldToken!=null) {
                fcmTokenRepository.delete(oldToken);
            }
            throw new UsernameNotFoundException("Member not found");
        }
        logger.info("Member is {}",member);
       FcmToken oldToken=fcmTokenRepository.findByToken(tokenRequest.getFcmToken());
       if(oldToken==null) {

       if(notificationSendService.validateFcmToken(tokenRequest.getFcmToken())){
           System.out.println("Fcm token is valid");
           FcmToken fcmToken=new FcmToken();
           fcmToken.setDeviceType(tokenRequest.getDeviceType());
           fcmToken.setToken(tokenRequest.getFcmToken());
           fcmToken.setMember(member);
           fcmTokenRepository.save(fcmToken);
       }else {
           System.out.println("Fcm token is not valid");
           throw new IllegalArgumentException("Fcm token is not valid");
       }
       }else {
           oldToken.setMember(member);
           fcmTokenRepository.save(oldToken);
       }
    }

    @Override
    public void deleteFcmToken(String fcmToken) {
        FcmToken savedToken=fcmTokenRepository.findByToken(fcmToken);
        if(savedToken!=null) {
            fcmTokenRepository.delete(savedToken);
            logger.info("FCM token deleted {}",fcmToken);
        }
    }

}
