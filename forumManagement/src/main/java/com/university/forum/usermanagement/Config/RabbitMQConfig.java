package com.university.forum.usermanagement.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String memberManagementExchange = "member-management-exchange";
    public static final String REPORTS_QUEUE = "reports.queue";

    @Bean
    public Queue reportsQueue() {
        return new Queue(REPORTS_QUEUE, true); // durable queue
    }

    @PostConstruct
    public void init() {
        System.out.println("📦 RabbitMQConfig FORUM loaded!");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange memberManagementExchange() {
        return new TopicExchange(memberManagementExchange);
    }

    // 🔁 Separate queues for FORUM MS
    @Bean
    public Queue forumStudentCreatedQueue() {
        return new Queue("forum-member-student-created-queue", true);
    }

    @Bean
    public Binding studentBinding(Queue forumStudentCreatedQueue, TopicExchange memberManagementExchange) {
        return BindingBuilder.bind(forumStudentCreatedQueue)
                .to(memberManagementExchange)
                .with("member.student.created");
    }

    @Bean
    public Queue forumProfessorCreatedQueue() {
        return new Queue("forum-member-professor-created-queue", true);
    }

    @Bean
    public Binding professorBinding(Queue forumProfessorCreatedQueue, TopicExchange memberManagementExchange) {
        return BindingBuilder.bind(forumProfessorCreatedQueue)
                .to(memberManagementExchange)
                .with("member.professor.created");
    }

}
