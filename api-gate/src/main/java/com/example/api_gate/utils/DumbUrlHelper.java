package com.example.api_gate.utils;

import java.net.URL;

public class DumbUrlHelper {
    private String url;
    public DumbUrlHelper(String url) { this.url = url; }

    public DumbUrlHelper removeEndingSlashes()
    {
        if(url.endsWith("/") || url.endsWith("\\")) {
            url = url.substring(0, url.length() - 1);
            return removeEndingSlashes();
        }
        return this;
    }

    public DumbUrlHelper removeSpaces()
    {
        url = url.replace(" ","");
        return this;
    }

    public boolean isValid()
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return url;
    }
}
