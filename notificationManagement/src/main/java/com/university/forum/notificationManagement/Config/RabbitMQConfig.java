package com.university.forum.notificationManagement.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
@Configuration
@EnableRabbit
public class RabbitMQConfig {


    private static final String memberNotificationExchange="member.notification.exchange";
    private static final String memberDeleteFcmTokenQueue="member.deleteFcmToken.queue";
    private static final String passwordExchange = "password.reset.exchange";
    private static final String passwordRoutingKey = "password.reset.email";
    private static final String memberManagementExchange = "member-management-exchange";
    private final DefaultClassMapper classMapper;

    public RabbitMQConfig(DefaultClassMapper classMapper) {
        this.classMapper = classMapper;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper);
        return converter;
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
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }



    @Bean
    public TopicExchange memberManagementExchange() {
        return new TopicExchange(memberManagementExchange);
    }

    @Bean
    public Queue memberStudentCreatedQueue() {
        return new Queue("member-student-created-queue", true);
    }

    @Bean
    public Binding studentBinding(Queue memberStudentCreatedQueue, TopicExchange memberManagementExchange) {
        return BindingBuilder.bind(memberStudentCreatedQueue)
                .to(memberManagementExchange)
                .with("member.student.created");
    }

    @Bean
    public Queue memberProfessorCreatedQueue() {
        return new Queue("member-professor-created-queue", true);
    }

    @Bean
    public Binding professorBinding(Queue memberProfessorCreatedQueue, TopicExchange memberManagementExchange) {
        return BindingBuilder.bind(memberProfessorCreatedQueue)
                .to(memberManagementExchange)
                .with("member.professor.created");
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("notifications.direct.queue", true);
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange("notifications.direct.exchange", true, false);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue)
                .to(notificationExchange)
                .with("notifications.direct-key");
    }

    @Bean
    public DirectExchange memberNotificationExchange() {
        return new DirectExchange(memberNotificationExchange);
    }

    @Bean
    public Queue memberDeleteFcmTokenQueue() {
        return new Queue(memberDeleteFcmTokenQueue, true);
    }

    @Bean
    public Binding memberDeleteFcmTokenBinding(Queue memberDeleteFcmTokenQueue, DirectExchange memberNotificationExchange) {
        return BindingBuilder.bind(memberDeleteFcmTokenQueue).to(memberNotificationExchange).with("member.deleteFcmToken");
    }
    @Bean
    public Queue passwordResetQueue() {
        return new Queue("password.reset.queue", true);
    }

    @Bean
    public DirectExchange passwordResetExchange() {
        return new DirectExchange(passwordExchange);
    }

    @Bean
    public Binding passwordResetBinding() {
        return BindingBuilder
                .bind(passwordResetQueue())
                .to(passwordResetExchange())
                .with(passwordRoutingKey);
    }

}