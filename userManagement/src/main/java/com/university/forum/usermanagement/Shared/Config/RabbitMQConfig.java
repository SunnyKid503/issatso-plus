package com.university.forum.usermanagement.Shared.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    private final String memberNotificationExchange="member.notification.exchange";
    private final String memberDeleteFcmTokenQueue="member.deleteFcmToken.queue";

    private final String passwordExchange = "password.reset.exchange";
    private final String passwordRoutingKey = "password.reset.email";

    public static final String memberManagementExchange = "member-management-exchange";

    public static final String REPORTS_QUEUE = "reports.queue";

    @Bean
    public Queue reportsQueue() {
        return new Queue(REPORTS_QUEUE, true); // durable queue
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
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(new MessageListenerAdapter(new MyMessageListener(), jackson2JsonMessageConverter));

        return container;
    }

    @Bean
    public Queue notificationDeleteFcmTokenQueue() {
        return new Queue(memberDeleteFcmTokenQueue, true);
    }
    @Bean
    public DirectExchange notificationDeleteFcmTokenExchange() {
        return new DirectExchange(memberNotificationExchange);
    }
    @Bean Binding notificationDeleteFcmTokenBinding(Queue notificationDeleteFcmTokenQueue, DirectExchange notificationDeleteFcmTokenExchange) {
            return BindingBuilder.bind(notificationDeleteFcmTokenQueue).to(notificationDeleteFcmTokenExchange).with("member.deleteFcmToken");
    }

    @Bean
    public TopicExchange memberManagementExchange() {
        return new TopicExchange(memberManagementExchange);
    }







    @Bean
    public Queue myQueue() {
        return new Queue("testQueue", true);
    }


    @Bean
    public Queue forumServiceQueue() {
        return new Queue("forum-service-queue", true);
    }

    @Bean
    public Binding binding(Queue forumServiceQueue, TopicExchange memberManagementExchange) {
        return BindingBuilder.bind(forumServiceQueue).to(memberManagementExchange).with("member.professor.created");
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
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
