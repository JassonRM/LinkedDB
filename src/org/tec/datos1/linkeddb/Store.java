package org.tec.datos1.linkeddb;

import java.io.File;

public class Store {
    private String name;
    private CircularList documents;
    private File dir;

    public Store(String name){
        this.name = name;
        this.dir = new File(name);
        this.dir.mkdirs();
    }

    public String getName() {
        return name;
    }

//    public void print() {
//        System.out.println(this.name);
//    }

    public void newDocument(){
        Document document = new Document();
        documents.append(document);
    }
}
