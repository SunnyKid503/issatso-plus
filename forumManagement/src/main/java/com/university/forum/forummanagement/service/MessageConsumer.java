package com.university.forum.forummanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.forum.usermanagement.Dtos.Message.ProfessorMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class MessageConsumer {

//    @RabbitListener(queues = "testQueue")
//    public void receiveMessage(String message) {
//        System.out.println("\uD83D\uDCE9 Received: "+message);
//    }
//
//    @RabbitListener(queues = "secondQueue")
//    public void receiveSecondMessage(String message) {
//        System.out.println("Second queue received : "+message);
//    }
//
//    @RabbitListener(queues = "forum-service-queue")
//    public void receiveMessage(ProfessorMessage message) {
//        System.out.println("📩 Received: " + message);
//    }



}
