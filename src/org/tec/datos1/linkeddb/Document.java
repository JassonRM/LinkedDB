package org.tec.datos1.linkeddb;

import java.util.HashMap;

public class Document {
    private String name;
    private HashMap<String, HashMap<String, String>> atributes;


    
    public Document(String name) {
        this.name = name;
        
    }

    public String getName() {
        return name;
    }
}
