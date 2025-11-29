package com.university.forum.notificationManagement.Config;

import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitClassMapperConfig{
    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setIdClassMapping(ClassMapperConfig.getAllDtoMappings());
        mapper.setTrustedPackages(
                "com.university.forum.usermanagement.Shared.Dtos.Messages",
                "com.university.forum.notificationManagement.Dtos.Messages",
                "com.university.forum.usermanagement.Dtos.Message"
        );
        return mapper;
    }

}
