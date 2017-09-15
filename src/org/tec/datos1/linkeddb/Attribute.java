package org.tec.datos1.linkeddb;

public class Attribute {

    private String name;
    private String type;
    private String specialKey;
    private String foreignKey;
    private boolean required;
    private String defaultValue;

    public Attribute(String name, String type, String specialKey, String foreignKey, boolean required, String defaultValue) {
        this.name = name;
        this.type = type;
        this.specialKey = specialKey;
        this.foreignKey = foreignKey;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecialKey() {
        return specialKey;
    }

    public void setSpecialKey(String specialKey) {
        this.specialKey = specialKey;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
