package com.university.forum.usermanagement.Shared.Services;

import com.university.forum.usermanagement.Shared.Dtos.Messages.NewPasswordMessage;
import com.university.forum.usermanagement.Shared.Dtos.Messages.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationSender {


    private final RabbitTemplate rabbitTemplate;
    private final String exchange = "notifications.direct.exchange";
    private final String routingKey = "notifications.direct-key";

    public NotificationSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(NotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("✅ Notification sent: " + message);
    }

    public void sendEmailNotification(NotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public void sendPasswordResetEmail(NewPasswordMessage message) {
        rabbitTemplate.convertAndSend("password.reset.exchange", "password.reset.email", message);
        System.out.println("📩 Password reset message sent: " + message);
    }

}
