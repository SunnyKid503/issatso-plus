package com.example.api_gate.services;

import com.example.api_gate.dtos.UrlsCollection;
import com.example.api_gate.utils.DumbUrlHelper;
import com.example.api_gate.utils.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UrlsExtractor extends AbstractService<UrlsCollection>{

    @Value("${servers.filename}")
    private String serversCollectionFileName;

    private UrlsCollection ensureCorrectUrlFormat(UrlsCollection urlsCollection)
    {
        List<String> urls = new ArrayList<>();

        for(String url: urlsCollection.getServers()){
            DumbUrlHelper helper = new DumbUrlHelper(url)
                    .removeEndingSlashes()
                    .removeSpaces();

            if(helper.isValid())
                urls.add(helper.toString());
            else
                logger.warn(String.format(
                        "Invalid url: '%s' - this url will be skipped, correct it and rerun the app.",
                        url)
                );
        }
        urlsCollection.setServers(urls);
        return urlsCollection;
    }

    @Override
    public UrlsCollection Behave() {
        try {
            UrlsCollection urls = JsonParser.fromJson(serversCollectionFileName, UrlsCollection.class);
            return ensureCorrectUrlFormat(urls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
