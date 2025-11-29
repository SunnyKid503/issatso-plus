package com.university.forum.usermanagement.Shared.Services;


import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.MemberManagement.Dtos.Message.ProfessorMessage;
import com.university.forum.usermanagement.MemberManagement.Dtos.Message.StudentMessage;
import com.university.forum.usermanagement.MemberManagement.Models.Member;
import com.university.forum.usermanagement.MemberManagement.Models.Professor;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import com.university.forum.usermanagement.MemberManagement.Models.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String queueName = "testQueue";
    private final String exchangeName = "member-management-exchange";

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendMemberCreatedMessage(Member member, String memberType) {
        Object dto = switch (memberType.toLowerCase()) {
            case "student" -> convertMemberToStudentDto((Student) member);
            case "professor" -> convertMemberToProfessorDto((Professor) member);
            default -> throw new IllegalArgumentException("Unknown member type: " + memberType);
        };

        String routingKey = "member." + memberType + ".created";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, dto);
        System.out.println("✅ Sent new member created to [" + routingKey + "]: " + dto);
    }



    public ProfessorMessage convertMemberToProfessorDto(Professor member) {
        if (member == null) {
            return null;
        }
        ProfessorMessage professorMessage=new ProfessorMessage();
        professorMessage.setId(member.getId());
        professorMessage.setFirstName(member.getFirstName());
        professorMessage.setLastName(member.getLastName());
        professorMessage.setDob(member.getDob());
        professorMessage.setAddress(member.getAddress());
        professorMessage.setAddressEmail(member.getAddressEmail());
        professorMessage.setSex(member.getSex());
        professorMessage.setProfileImageUrl(member.getProfileImageUrl());
        professorMessage.setPhoneNumber(member.getPhoneNumber());
        professorMessage.setLinkedInProfileUrl(member.getLinkedInProfileUrl());
        professorMessage.setCin(member.getCin());
        professorMessage.setClassGroupIds(member.getClassGroups().stream().map(ClassGroup::getId).collect(Collectors.toSet()));
        professorMessage.setRoleIds(member.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        return professorMessage;
    }


    public StudentMessage convertMemberToStudentDto(Student member) {
        if (member == null) {
            return null;
        }
        StudentMessage studentMessage=new StudentMessage();
        studentMessage.setId(member.getId());
        studentMessage.setFirstName(member.getFirstName());
        studentMessage.setLastName(member.getLastName());
        studentMessage.setDob(member.getDob());
        studentMessage.setAddress(member.getAddress());
        studentMessage.setAddressEmail(member.getAddressEmail());
        studentMessage.setSex(member.getSex());
        studentMessage.setProfileImageUrl(member.getProfileImageUrl());
        studentMessage.setPhoneNumber(member.getPhoneNumber());
        studentMessage.setLinkedInProfileUrl(member.getLinkedInProfileUrl());
        studentMessage.setCin(member.getCin());
        studentMessage.setClassGroupId(member.getClassGroup().getId());
        studentMessage.setStudentNumber(member.getStudentNumber());
        studentMessage.setRoleIds(member.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        return studentMessage;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("✅ Sent: " + message);
    }
}


