package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

/**
 * Esta clase denota un atributo de un documento y todas las caracteristicas necesarias
 */
public class Attribute {

    private String name;
    private String type;
    private String specialKey;
    private String foreignKey;
    private boolean required;
    private String defaultValue;

    /**
     * Constructor que utiliza Jackson para mapear los datos guardados a una nueva instancia
     * @param name Nombre del atributo
     * @param type Tipo de dato que contiene ese atributo
     * @param specialKey Indica si el atributo es una llave primaria o foranea
     * @param foreignKey Si el atributo es llave foranea almacena la direccion de referencia
     * @param required Indica si el valor del atributo es requerido
     * @param defaultValue Almacena el valor por defecto que toma el campo del atributo en caso de que no sea requerido
     */
    @JsonCreator
    public Attribute(@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("specialKey") String specialKey, @JsonProperty("foreignKey") String foreignKey, @JsonProperty("required") boolean required, @JsonProperty("defaultValue") String defaultValue) {
        this.name = name;
        this.type = type;
        this.specialKey = specialKey;
        this.foreignKey = foreignKey;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    /**
     * Constructor para la creacion de un nuevo atributo
     * @param input Diccionario de entrada con todas las propiedades del atributo mapeadas a una llave.
     */
    public Attribute(LinkedHashMap<String, String> input) {
        this.name = input.get("name");
        this.type = input.get("type");
        this.specialKey = input.get("specialKey");
        this.foreignKey = input.get("foreignKey");
        if (input.get("required") == "true"){
            this.required = true;
        }else{
            this.required = false;
        }
        this.defaultValue = input.get("defaultValue");
    }

    /**
     * @return Devuelve el nombre del atributo
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Recibe el nuevo nombre dle atributo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Devuelve el tipo del atributo
     */
    public String getType() {
        return type;
    }

    /**
     * @param type Establece el tipo del atributo
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Devuelve el tipo especial del atributo
     */
    public String getSpecialKey() {
        return specialKey;
    }

    /**
     * @param specialKey Establece el tipo especial del atributo
     */
    public void setSpecialKey(String specialKey) {
        this.specialKey = specialKey;
    }

    /**
     * @return Devuelve la direccion de la llave foranea
     */
    public String getForeignKey() {
        return foreignKey;
    }

    /**
     * @param foreignKey Establece la direccion de la llave foranea
     */
    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * @return Devuelve si el atributo es requerido o no
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required Establece si el atributo es requerido o no
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return Devuelve el valor por defecto del atributo
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue Establece el valor por defecto del atributo
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
