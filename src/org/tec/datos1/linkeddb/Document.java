package org.tec.datos1.linkeddb;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.LinkedList;

public class Document implements JSONprinter{
    private String name;
    //    private HashMap<String, HashMap<String, String>> atributes; // No se si se ocupa
    private ObservableList<Attribute> attributes;

    public Document(String name, ObservableList<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public String toJSON() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public ObservableList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ObservableList<Attribute> attributes) {
        this.attributes = attributes;
    }
}
