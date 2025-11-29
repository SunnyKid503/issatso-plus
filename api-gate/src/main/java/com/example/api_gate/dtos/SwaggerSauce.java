package com.example.api_gate.dtos;

import java.util.HashMap;

public class SwaggerSauce {
    HashMap<String, Object> Paths;

    public HashMap<String, Object> getPaths() {
        return Paths;
    }

    public void setPaths(HashMap<String, Object> paths) {
        Paths = paths;
    }
}
