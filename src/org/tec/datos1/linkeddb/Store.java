package org.tec.datos1.linkeddb;

import java.io.File;
import java.util.HashMap;

public class Store implements JSONprinter {
    private String name;
    private CircularList documents;
    private File dir;

    public Store(String name){
        this.name = name;
        this.dir = new File(App.path + name); //Tiene que servir para multiples sistemas operativos
        this.dir.mkdirs();
        this.documents = new CircularList();
    }

    @Override
    public JSONStore toJSON() {
        return new JSONStore(this.name, this.documents);
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
