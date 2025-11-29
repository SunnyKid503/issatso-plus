package com.university.forum.usermanagement.Services;

import com.university.forum.usermanagement.Dtos.Message.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberNotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String memberNotificationExchange="member.notification.exchange";
    private final String exchange = "notifications.direct.exchange";
    private final String routingKey = "notifications.direct-key";

    public MemberNotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDeleteFcmToken(String fcmToken) {
        Map<String, Object> message = new HashMap<>();
        message.put("fcmToken", fcmToken);
        rabbitTemplate.convertAndSend(memberNotificationExchange, "member.deleteFcmToken", message);
    }

    public void sendNotification(NotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("✅ Notification sent: " + message);
    }
}
