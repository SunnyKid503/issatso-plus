package com.university.forum.notificationManagement.Services.Impl;

import com.university.forum.notificationManagement.Dtos.Messages.NotificationMessage;
import com.university.forum.notificationManagement.Dtos.Requests.GetNotificationRequest;
import com.university.forum.notificationManagement.Dtos.Requests.NotificationRequest;
import com.university.forum.notificationManagement.Dtos.Responses.NotificationResponse;
import com.university.forum.notificationManagement.Exceptions.ElementNotFoundException;
import com.university.forum.notificationManagement.Models.Enums.DeliveryStatus;
import com.university.forum.notificationManagement.Models.Enums.NotificationTopic;
import com.university.forum.notificationManagement.Models.Enums.NotificationType;
import com.university.forum.notificationManagement.Models.FcmToken;
import com.university.forum.notificationManagement.Models.Member;
import com.university.forum.notificationManagement.Models.Notification;
import com.university.forum.notificationManagement.Models.NotificationMember;
import com.university.forum.notificationManagement.RabbitMQListeners.NotificationListener;
import com.university.forum.notificationManagement.Repositories.FcmTokenRepository;
import com.university.forum.notificationManagement.Repositories.MemberRepository;
import com.university.forum.notificationManagement.Repositories.NotificationMemberRepository;
import com.university.forum.notificationManagement.Repositories.NotificationRepository;
import com.university.forum.notificationManagement.Services.NotificationSendService;
import com.university.forum.notificationManagement.Services.NotificationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {


    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final NotificationSendService notificationSendService;
    private final NotificationMemberRepository notificationMemberRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final MemberRepository memberRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationSendService notificationSendService, NotificationMemberRepository notificationMemberRepository, FcmTokenRepository fcmTokenRepository, MemberRepository memberRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationSendService = notificationSendService;
        this.notificationMemberRepository = notificationMemberRepository;
        this.fcmTokenRepository = fcmTokenRepository;
        this.memberRepository = memberRepository;
    }


    @Override
    public void sendTestNotification(String token, String title, String body) {
        Long id= 123L;
        notificationSendService.sendNotification(token,id, title, body,"","","","",LocalDateTime.now());
    }

    @Override
    public void sendTestNotificationToUserById(UUID userId, NotificationRequest request) {
        Iterable<FcmToken> fcmTokens=fcmTokenRepository.findByMemberId(userId);
        Long id= 123L;

        for (FcmToken fcmToken : fcmTokens) {
            notificationSendService.sendNotification(fcmToken.getToken(),id, request.getTitle(), request.getBody(),"","","","",LocalDateTime.now());
        }

    }

    @Override
    @Transactional
    public void sendNotification(NotificationMessage message) {
        logger.info("STAARTTTTT The receiversId list is :"+message.getReceiversIds());

        Notification notification=new Notification();
        notification.setNotificationType(message.getType());
        notification.setBody(message.getBody());
        notification.setTopic(message.getNotificationTopic());
        notification.setTitle(message.getTitle());notification.setLink(Optional.ofNullable(message.getLink()).orElse(""));
        notification.setImageUrl(Optional.ofNullable(message.getImageUrl()).orElse(""));

        notificationRepository.save(notification);
        List<Member> membersId=memberRepository.findAllById(message.getReceiversIds());

        logger.info("SECONDD The membersId list is :"+membersId);

        List<NotificationMember> notificationMembers = new ArrayList<>();
        for (Member member : membersId) {
            NotificationMember notificationMember = new NotificationMember();
            notificationMember.setNotification(notification);
            notificationMember.setMember(member);
            notificationMember.setDeliveryStatus(DeliveryStatus.PENDING);
            notificationMember.setDeliveredAt(null);
            notificationMember.setViewedAt(null);
            notificationMembers.add(notificationMember);
        }

        notificationMemberRepository.saveAll(notificationMembers);

        notification.setRecipients(notificationMembers);
        notificationRepository.save(notification);

        //lets send the notifications now since everything is  good right ?

        List<UUID> receiversId = membersId.stream().map(Member::getId).collect(Collectors.toList());
        logger.info("The receiversId list is :"+receiversId);

        Iterable<FcmToken> fcmTokens = fcmTokenRepository.findAllByMemberIds(receiversId);

        Map<UUID, List<FcmToken>> tokensMap = new HashMap<>();
        for (FcmToken token : fcmTokens) {
            tokensMap.computeIfAbsent(token.getMember().getId(), k -> new ArrayList<>()).add(token);
        }
        for (UUID tokenId : tokensMap.keySet()) {

            logger.info("The fcmtoken map is id: {}, token: {}", tokenId.toString(), tokensMap.get(tokenId));
        }
        for (NotificationMember notificationMember : notificationMembers) {
            List<FcmToken> tokens = tokensMap.get(notificationMember.getMember().getId());
            if (tokens != null) {
                for (FcmToken token : tokens) {
                    try {
                        notificationSendService.sendNotification(token.getToken(), notification.getId(), notification.getTitle(), notification.getBody(),notification.getNotificationType().toString(),notificationMember.getDeliveryStatus().toString(),notification.getImageUrl(), notification.getLink(),notification.getCreatedAt()!=null ? notification.getCreatedAt():LocalDateTime.now());
                        notificationMember.setDeliveryStatus(DeliveryStatus.DELIVERED);
                    } catch (Exception e) {
                        notificationMember.setDeliveryStatus(DeliveryStatus.FAILED);
                    } finally {
                        notificationMember.setDeliveredAt(LocalDateTime.now());
                        notificationMemberRepository.save(notificationMember);
                    }
                }
            }
        }

        logger.info("✅ Notification sent to {} members.",receiversId.size());

    }

    @Override
    public List<NotificationResponse> getNotifications(UUID memberId, GetNotificationRequest notificationRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        List<NotificationResponse> notificationResponses = new ArrayList<>();
        List<NotificationMember> notificationMembers;


        Pageable pageable = PageRequest.of(0, notificationRequest.getNumberOfNotifications());

        if (notificationRequest.getLastNotificationId() == null || notificationRequest.getLastNotificationId()==0) {
            notificationMembers = notificationMemberRepository
                    .findByMemberIdOrderByNotificationCreatedAtDesc(memberId, pageable);
        } else {
            notificationMembers = notificationMemberRepository
                    .findByMemberIdAndNotificationIdLessThanOrderByNotificationCreatedAtDesc(
                            memberId,
                            notificationRequest.getLastNotificationId(),
                            pageable
                    );
        }

        notificationMembers.forEach(nm -> notificationResponses.add(
                new NotificationResponse(
                        nm.getNotification().getId(),
                        nm.getNotification().getTitle(),
                        nm.getNotification().getNotificationType(),
                        nm.getNotification().getBody(),
                        nm.getNotification().getImageUrl(),
                        nm.getNotification().getLink(),
                        nm.getNotification().getTopic(),
                        nm.getDeliveredAt(),
                        nm.getViewedAt(),
                        nm.getDeliveryStatus()
                )
        ));

        return notificationResponses;
    }

    @Override
    public NotificationResponse viewNotification(UUID memberId, Long id) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));
        Notification notification=notificationRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Notification not found"));
        NotificationMember notificationMember=notificationMemberRepository.findByNotificationIdAndMemberId(id,memberId).orElse(null);
        if(notificationMember!=null) {
            if(!"READ".equals(notificationMember.getDeliveryStatus().toString())){
                notificationMember.setDeliveryStatus(DeliveryStatus.READ);
                notificationMember.setViewedAt(LocalDateTime.now());
            }

        }else{
            logger.warn("NotificationMember not found for notification ID: " + id);
            notificationMember = new NotificationMember(member, notification, DeliveryStatus.READ, LocalDateTime.now());

        }
        notificationMember=notificationMemberRepository.save(notificationMember);
        NotificationResponse notificationResponse=  new NotificationResponse(
                notificationMember.getNotification().getId(),
                notificationMember.getNotification().getTitle(),
                notificationMember.getNotification().getNotificationType(),
                notificationMember.getNotification().getBody(),
                notificationMember.getNotification().getImageUrl(),
                notificationMember.getNotification().getLink(),
                notificationMember.getNotification().getTopic(),
                notificationMember.getDeliveredAt(),
                notificationMember.getViewedAt(),
                notificationMember.getDeliveryStatus()
        );
        return notificationResponse;
    }

    @Override
    @Transactional
    public void deleteNotificationById(UUID memberId, Long id) {
        notificationMemberRepository.findByNotificationIdAndMemberId(id, memberId)
                .ifPresentOrElse(
                        notificationMemberRepository::delete,
                        () -> {
                            throw new ElementNotFoundException("Notification not found for member");
                        }
                );
    }

    @Override
    @Transactional
    public List<NotificationResponse> viewNotificationList(UUID memberId, List<Long> notificationIds) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));
        Iterable<Notification> notifications=notificationRepository.findAllById(notificationIds);
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationMember notificationMember=notificationMemberRepository.findByNotificationIdAndMemberId(notification.getId(),memberId).orElse(null);
            if(notificationMember!=null) {
                if(!"READ".equals(notificationMember.getDeliveryStatus().toString())  ||  notificationMember.getViewedAt()==null ){
                    notificationMember.setDeliveryStatus(DeliveryStatus.READ);
                    notificationMember.setViewedAt(LocalDateTime.now());
                }

            }else{
                logger.warn("NotificationMember not found for notification ID: " + notification.getId());
                notificationMember = new NotificationMember(member, notification, DeliveryStatus.READ, LocalDateTime.now());

            }
            notificationMember=notificationMemberRepository.save(notificationMember);
            notificationResponses.add(new NotificationResponse(
                    notificationMember.getNotification().getId(),
                    notificationMember.getNotification().getTitle(),
                    notificationMember.getNotification().getNotificationType(),
                    notificationMember.getNotification().getBody(),
                    notificationMember.getNotification().getImageUrl(),
                    notificationMember.getNotification().getLink(),
                    notificationMember.getNotification().getTopic(),
                    notificationMember.getDeliveredAt(),
                    notificationMember.getViewedAt(),
                    notificationMember.getDeliveryStatus()
            ));
        }
        return notificationResponses;
    }


}
