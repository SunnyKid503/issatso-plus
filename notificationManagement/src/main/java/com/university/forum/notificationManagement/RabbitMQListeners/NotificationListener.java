package com.university.forum.notificationManagement.RabbitMQListeners;

import com.university.forum.notificationManagement.Dtos.Messages.NotificationMessage;
import com.university.forum.notificationManagement.Models.Member;
import com.university.forum.notificationManagement.Repositories.MemberRepository;
import com.university.forum.notificationManagement.Services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private  final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


//    @RabbitListener(queues = "notifications.direct.queue")
//    public void receiveTestNotification(NotificationMessage message) {
//        logger.info("✅ Received Test Notification from user-management: {}", message);
//
//        List<UUID> receiversIds = message.getReceiversIds();
//        for (UUID receiverId : receiversIds) {
//                logger.info("{} received", receiverId);
//
//        }
//    }


    @RabbitListener(queues = "notifications.direct.queue")
    public void receiveNotification(NotificationMessage message) {
        logger.info("✅ Received Notification from user-management: {}", message);
        try {
            if (message.isValid()) {
                notificationService.sendNotification(message);
            } else {
                logger.warn("{} is NOT valid", message.getReceiversIds());
            }
        } catch (Exception e) {
            logger.error("❌ Error processing notification: {}", e.getMessage());
            throw e;
        }

    }

}