package com.university.forum.usermanagement.Services;


import com.university.forum.usermanagement.Config.RabbitMQConfig;
import com.university.forum.usermanagement.Dtos.Message.ReportMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportProducer {
    private final static Logger logger= LoggerFactory.getLogger(ReportProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public ReportProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReport(ReportMessage report) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.REPORTS_QUEUE,report);
        logger.info("{} report sent to report MS : {}",report.getTargetType(),report);
    }
}
