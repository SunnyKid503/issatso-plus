package com.example.api_gate.dtos;

import java.util.List;

public class UrlsCollection{
    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    private List<String> servers;

}
