package com.university.forum.reportmanagement.RabbitMQListeners;

import com.university.forum.reportmanagement.Config.RabbitMQConfig;
import com.university.forum.reportmanagement.Dtos.Messages.ReportMessage;
import com.university.forum.reportmanagement.Models.Report;
import com.university.forum.reportmanagement.Services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportListener {

    private final static Logger logger= LoggerFactory.getLogger(ReportListener.class);
    private final ReportService reportService;

    public ReportListener(ReportService reportService) {
        this.reportService = reportService;
    }


    @RabbitListener(queues = RabbitMQConfig.REPORTS_QUEUE)
    public void processReport(ReportMessage reportMessage) {
        logger.info("Report received {}",reportMessage);
        reportService.processReport(reportMessage);
    }

}


