package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    /**
     * Constructor que establece el nombre y la lista de atributos del objeto, ademas inicializa la lista de objetos
     * @param name Nombre del documento
     * @param attributes Lista con los atributos del objeto
     */
    public Document(String name, Object[] attributes) {
        this.name = name;
        this.attributes = (Attribute[]) attributes;
        this.objects = new LinkedHashMap<String, LinkedHashMap<String, String>>();
    }

    /**
     * Constructor que recibe todos los datos de un archivo JSON
     * @param name Nombre del documento
     * @param attributes lista con los atributos del objeto
     * @param objects Lista de los objetos contenidos en este documento
     */
    @JsonCreator
    public Document(@JsonProperty("name") String name, @JsonProperty("attributes") Object[] attributes, @JsonProperty("objects") LinkedHashMap<String, LinkedHashMap<String, String>> objects) {
        this.name = name;
        this.attributes = new Attribute[attributes.length];
        this.objects = objects;
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
    public void deleteAll() {
        this.objects = new LinkedHashMap<String, LinkedHashMap<String, String>>();
    }

    public LinkedHashMap<String, String> delete(String id) {
        if(objects.containsKey(id)){
            LinkedHashMap<String, String> object = objects.get(id);
            objects.remove(id);
            return object;
        } else {
            return null;
        }
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

}
