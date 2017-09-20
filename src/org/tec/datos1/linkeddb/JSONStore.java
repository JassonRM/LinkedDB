package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.HashMap;

public class JSONStore {
    private String name;
    private Object[] documents;
    private File dir;


    public JSONStore(String name, CircularList documents) {
        this.name = name;
        this.dir = new File(App.path + name); //Tiene que servir para multiples sistemas operativos
        this.dir.mkdirs();
        this.documents = documents.toJSONArray();
    }

    @JsonCreator
    public JSONStore(@JsonProperty("name") String name, @JsonProperty("dir") String dir, @JsonProperty("documents") String[] documents){
        this.name = name;
        this.documents = documents;
        this.dir = new File(dir);
    }

    public Store toStore(){
        return new Store(this.name, this.dir, this.documents);
    }

    public String getName() {
        return name;
    }

    public Object[] getDocuments() {
        return documents;
    }

    public File getDir() {
        return dir;
    }

    @Override
    public String toString(){
        return getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocuments(Object[] documents) {
        this.documents = documents;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }
}
