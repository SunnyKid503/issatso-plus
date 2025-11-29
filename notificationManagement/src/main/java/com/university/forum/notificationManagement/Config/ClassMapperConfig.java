package com.university.forum.notificationManagement.Config;

import java.util.HashMap;
import java.util.Map;

public class ClassMapperConfig {

    public static Map<String, Class<?>> getAllDtoMappings() {
        Map<String, Class<?>> map = new HashMap<>();
        map.put("com.university.forum.usermanagement.MemberManagement.Dtos.Message.ProfessorMessage", com.university.forum.notificationManagement.Dtos.Messages.ProfessorMessage.class);
        map.put("com.university.forum.usermanagement.MemberManagement.Dtos.Message.StudentMessage",com.university.forum.notificationManagement.Dtos.Messages.StudentMessage.class);
        map.put("com.university.forum.usermanagement.Shared.Dtos.Messages.NewPasswordMessage", com.university.forum.notificationManagement.Dtos.Messages.NewPasswordMessage.class);
        map.put("com.university.forum.usermanagement.Dtos.Message.NotificationMessage", com.university.forum.notificationManagement.Dtos.Messages.NotificationMessage.class);

        return map;
    }
}