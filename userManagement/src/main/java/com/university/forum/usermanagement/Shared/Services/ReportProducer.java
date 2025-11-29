package com.university.forum.usermanagement.Shared.Services;

import com.university.forum.usermanagement.MemberManagement.Dtos.Message.ReportMessage;
import com.university.forum.usermanagement.MemberManagement.Models.Report;
import com.university.forum.usermanagement.Shared.Config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportProducer {

    private static final Logger logger= LoggerFactory.getLogger(ReportProducer.class);
    private final RabbitTemplate rabbitTemplate;
    public ReportProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReport(ReportMessage reportMessage) {

        rabbitTemplate.convertAndSend(RabbitMQConfig.REPORTS_QUEUE,reportMessage);
        logger.info("Report sent to report MS : {}",reportMessage);
    }
}
