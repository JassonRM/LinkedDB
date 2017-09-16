package org.tec.datos1.linkeddb;

import java.io.File;
import java.util.HashMap;

public class Store implements JSONprinter {
    private String name;
    private CircularList documents;
    private File dir;

    public Store(String name){
        this.name = name;
        this.dir = new File("/Users/Jai/Desktop/Linked DB/" + name); //Tiene que servir para multiples sistemas operativos
        this.dir.mkdirs();
        this.documents = new CircularList();
    }

    @Override
    public HashMap<String, Object> toJSON() {
        HashMap<String, Object> store = new HashMap<>();
        store.put("name", this.name);
        store.put("dir", this.dir.getPath());
        store.put("documents", this.documents.toJSONArray());
        return store;
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

    public void setName(String name) {
        this.name = name;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }
}
