package com.example.api_gate.configurations;

import com.example.api_gate.dtos.UrlsCollection;
import com.example.api_gate.logging.RequestLogger;
import com.example.api_gate.services.UrlsExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class BeanFarm {

    @Bean
    public GlobalFilter requestLogger(){
        return new RequestLogger();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UrlsCollection urlsCollection(@Autowired UrlsExtractor extractor) throws IOException {
        return extractor.Behave();
    }
}

