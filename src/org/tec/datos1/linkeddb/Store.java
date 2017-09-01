package org.tec.datos1.linkeddb;

import java.io.File;

public class Store {
    private String name;
    private CircularList documents = new CircularList();
    private File dir;

    public Store(String name){
        this.name = name;
        this.dir = new File(name);
        this.dir.mkdirs();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }

    public void newDocument(Document document){
        this.documents.append(document);
    }

    public CircularList getDocuments() {
        return documents;
    }

    public void setDocuments(CircularList documents) {
        this.documents = documents;
    }
}
