package org.tec.datos1.linkeddb;

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

}
