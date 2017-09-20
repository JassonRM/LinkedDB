package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Esta clase denota un Store, el contenedor mas grande de la jerarquia
 */
public class Store implements JSONprinter {
    private String name;
    private CircularList documents;
    private File dir;

    /**
     * Constructor para la creacion de un nuevo store
     * @param name Nombre a asignar al nuevo store
     */
    public Store(String name){
        this.name = name;
        this.dir = new File(App.path + name); //Tiene que servir para multiples sistemas operativos
        this.dir.mkdirs();
        this.documents = new CircularList<Document>();
    }

    /**
     * Constructor para cargar los stores desde JSON
     * @param name Nombre de store
     * @param dir Directorio en el cual se encuentran almacenados los documentos
     * @param documents Lista de documentos que contiene el store
     */
    public Store(String name, File dir, Object[] documents){ // documents es un array con los nombres de los documentos
        this.name = name;
        this.dir = dir;
        this.documents = new CircularList();
        for(Object object: documents){
            String documentName = (String) object;
            ObjectMapper mapper = new ObjectMapper();
            try {
                Document document = mapper.readValue(new File(dir.toString() + "/" + documentName + ".json"), Document.class);
                this.documents.append(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Convierte el store en un objeto qque se puede serializar a JSON
     * @return JSONStore que si se puede convertir a JSON
     */
    @Override
    public JSONStore toJSON() {
        return new JSONStore(this.name, this.documents);
    }

    /**
     * @return Nombre del Store
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna un string caracteristico del store
     * @return Nombre del Store
     */
    @Override
    public String toString(){
        return getName();
    }

    /**
     * Anade un nuevo documento al store
     * @param document Documento que se desea anadir
     */
    public void newDocument(Document document){
        this.documents.append(document);
    }

    /**
     * @return La lista con los documentos del store
     */
    public CircularList getDocuments() {
        return documents;
    }

    /**
     * @param documents Establece los documentos del store
     */
    public void setDocuments(CircularList documents) {
        this.documents = documents;
    }

    /**
     * @param name Establece el nombre del store
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return El directorio de los documentos del store
     */
    public File getDir() {
        return dir;
    }

    /**
     * @param dir Establece el directorio de los documentos del store
     */
    public void setDir(File dir) {
        this.dir = dir;
    }

    /**
     * Guarda los documentos en archivos JSON dentro del directorio
     */
    public void saveDocuments() {
        for(Object value: documents){
            Document document = (Document) value;
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(new File(dir.toString() + "/" + document.getName() + ".json"), document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
