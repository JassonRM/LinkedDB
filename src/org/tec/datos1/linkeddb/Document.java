package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Denota un documento perteneciente a un store dentro de la base de datos
 */
public class Document implements JSONprinter{
    private String name;
    private LinkedHashMap<String, LinkedHashMap<String, String>> objects;
    private Attribute[] attributes;
    private LinkedList<String> foreignKeys;

    /**
     * Constructor que establece el nombre y la lista de atributos del objeto, ademas inicializa la lista de objetos
     * @param name Nombre del documento
     * @param attributes Lista con los atributos del objeto
     */
    public Document(String name, Object[] attributes) {
        this.name = name;
        this.attributes = (Attribute[]) attributes;
        this.objects = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        this.foreignKeys = new LinkedList<>();
    }

    /**
     * Constructor que recibe todos los datos de un archivo JSON
     * @param name Nombre del documento
     * @param attributes lista con los atributos del objeto
     * @param objects Lista de los objetos contenidos en este documento
     */
    @JsonCreator
    public Document(@JsonProperty("name") String name, @JsonProperty("attributes") Object[] attributes, @JsonProperty("objects") LinkedHashMap<String, LinkedHashMap<String, String>> objects, @JsonProperty("foreignKeys") LinkedList<String> foreignKeys) {
        this.name = name;
        this.attributes = new Attribute[attributes.length];
        this.objects = objects;
        this.foreignKeys = foreignKeys;
        int count = 0;
        for(Object object: attributes){
            LinkedHashMap<String, Object> input = (LinkedHashMap<String, Object>)  object;
            this.attributes[count] = new Attribute(input);
            count++;
        }
    }

    /**
     * Retorna solamente el nombre al llamar el metodo toJSON
     * @return el nombre del documento
     */
    @Override
    public String toJSON() {
        return this.name;
    }

    /**
     * Retorna un string que describe al documento
     * @return el nombre del documento
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * @return el nombre del documento
     */
    public String getName() {
        return name;
    }

    /**
     * @return la lista de atributos
     */
    public Attribute[] getAttributes() {
        return attributes;
    }

    /**
     * @param attributes establece la lista de atributos
     */
    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    /**
     * Se encarga de buscar un atributo por su nombre
     * @param name nombre del atributo a buscar
     * @return la instancia del atributo encontrado
     */
    public Attribute searchAttribute(String name){
        for(Attribute attribute : attributes){
            if (attribute.getName().equals(name)){
                return attribute;
            }
        }
        return null;
    }

    /**
     * Se encarga de buscar cual atributo es la llave primaria del documento
     * @return la instancia del atributo de tipo especial llave primaria
     */
    public Attribute searchPrimaryKey(){
        for(Attribute attribute : attributes){
            if (attribute.getSpecialKey().equals("Primary key")){
                return attribute;
            }
        }
        System.out.println("No hay primaria");
        return null;
    }

    /**
     * Se encarga de buscar cual atributo es la llave foranea del documento
     * @return la instancia del atributo de tipo especial llave primaria
     */
    public Attribute searchForeignKey(){
        for(Attribute attribute : attributes){
            if (attribute.getSpecialKey().equals("Foreign key")){
                return attribute;
            }
        }
        System.out.println("No hay foranea");
        return null;
    }

    /**
     * Anade un objeto a la lista de objetos del documento
     * @param object instancia del objeto que se desea anadir
     */
    public void addObject(LinkedHashMap<String, String> object){
        objects.put(object.get(searchPrimaryKey().getName()), object);
    }

    /**
     * @return la lista de objetos del documento
     */
    public LinkedHashMap<String, LinkedHashMap<String, String>> getObjects() {
        return objects;
    }

    /**
     * Elimina todos los objetos del documento
     */
    public boolean deleteAll() {
        if(!foreignKeys.isEmpty()){
            for(String key: foreignKeys){
                String[] keyAddress = key.split("/");
                Store store = (Store) App.database.search(keyAddress[0]);
                Document document = (Document) store.getDocuments().search(keyAddress[1]);
                String referencedAttribute = document.searchForeignKey().getForeignKey().split("/")[2];
                for(LinkedHashMap<String, String> object: objects.values()){
                    if(!document.searchObjectByAttribute(object.get(referencedAttribute), document.searchForeignKey().getName()).isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failure to delete");
                        alert.setHeaderText("Objects referenced");
                        alert.setContentText("At least one object in the document is referenced by another document. Delete all references before trying to delete");

                        alert.showAndWait();
                        return false;
                    }
                }
            }
        }
        this.objects = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        return true;
    }

    public LinkedHashMap<String, String> delete(String id) {
        if(objects.containsKey(id)){

            LinkedHashMap<String, String> object = objects.get(id);

            if(!foreignKeys.isEmpty()){
                for(String key: foreignKeys){
                    String[] keyAddress = key.split("/");
                    Store store = (Store) App.database.search(keyAddress[0]);
                    Document document = (Document) store.getDocuments().search(keyAddress[1]);
                    String referencedAttribute = document.searchForeignKey().getForeignKey().split("/")[2];

                    if(!document.searchObjectByAttribute(object.get(referencedAttribute), document.searchForeignKey().getName()).isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failure to delete");
                        alert.setHeaderText("Object referenced");
                        alert.setContentText("The object is referenced by at least another document. Delete all references before trying to delete");

                        alert.showAndWait();
                        return null;
                    }
                }
            }
            objects.remove(id);
            return object;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid key");
            alert.setHeaderText("Inexistent object");
            alert.setContentText("The key entered doesn't exists in this document");

            alert.showAndWait();
        }
        return null;
    }

    public LinkedList<HashMap<String,String>> searchObject(String input){
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        for(Map.Entry<String, LinkedHashMap<String, String>> object: objects.entrySet()){
            for(String attribute : object.getValue().values()){
                if(attribute.equals(input)){
                    result.add(object.getValue());
                }
            }
        }
        return result;
    }

    public void addForeignKey(String key){
        foreignKeys.add(key);
    }

    public LinkedList<String> getForeignKeys() {
        return foreignKeys;
    }

    public boolean checkForeignKey(String value) {
        Attribute foreignKey = searchForeignKey();
        String[] foreignKeyAddress = foreignKey.getForeignKey().split("/");
        Store store = (Store) App.database.search(foreignKeyAddress[0]);
        Document document = (Document) store.getDocuments().search(foreignKeyAddress[1]);
        if (document.searchObjectByAttribute(value, foreignKeyAddress[2]).isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public LinkedList<HashMap<String, String>> searchObjectByAttribute(String value, String attribute) {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        for(LinkedHashMap<String, String> object: objects.values()){
            if(object.get(attribute).equals(value)){
                result.add(object);
            }
        }
        return result;
    }
}
