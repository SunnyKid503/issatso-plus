package com.example.api_gate.services;

import com.example.api_gate.dtos.SwaggerSauce;
import com.example.api_gate.dtos.UrlsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class ApiExplorer extends AbstractService<HashMap<String, List<String>>>{
    @Value("${swagger.docs.path}")
    private String swaggerJsonUrl;
    private RestTemplate restTemplate;
    private UrlsCollection urlsCollection;
    private static final Logger logger = LoggerFactory.getLogger(ApiExplorer.class);
    @Autowired
    public ApiExplorer(RestTemplate restTemplate, UrlsCollection urlsCollection)
    {
        this.restTemplate = restTemplate;
        this.urlsCollection = urlsCollection;
    }

    private SwaggerSauce requestDefinition(String path)
    {
        try {
            ResponseEntity<SwaggerSauce> response = restTemplate.getForEntity(path, SwaggerSauce.class);
            return response.getBody();
        }
        catch(Exception ex){
            return null;
        }
    }

    @Override
    public HashMap<String, List<String>> Behave() {
        HashMap<String, List<String>> result = new HashMap<>();

        for(String baseUrl : urlsCollection.getServers()) {
            String path = baseUrl.concat(swaggerJsonUrl);
            SwaggerSauce sauce = requestDefinition(path);

            if(sauce == null || sauce.getPaths() == null ||sauce.getPaths().isEmpty()) {
                logger.warn(String.format("No definition found for path '%s'.", baseUrl));
                continue;
            }

            result.put(baseUrl, sauce.getPaths().keySet().stream().toList());
            logger.info(String.format("Path '%s' was registered successfully.", baseUrl));
        }

        return result;
    }
}
