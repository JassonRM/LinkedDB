package org.tec.datos1.linkeddb;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.IntPredicate;

public class NewObjectDialog {

    public Optional<HashMap<String, String>> showandwait(Document document){

        //Crea el dialogo
        Dialog<HashMap<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Object");


        //Agrega los botones
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        //Crea el contenedor principal
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        // Crea campos para cada atributo
        int count = 0;
        LinkedHashMap<String, Object> inputs = new LinkedHashMap<>();
        for(Attribute attribute : document.getAttributes()){
            grid.add(new Label(attribute.getName()), 0, count);
            if (!attribute.getType().equals("Date")){
                TextField input = new TextField();
                input.setPromptText(attribute.getName());
                grid.add(input, 1, count);
                inputs.put(attribute.getName(), input);
            }else{
                DatePicker input = new DatePicker();
                grid.add(input, 1, count);
                inputs.put(attribute.getName(), input);
            }
            count++;
        }

        // Valida las entradas
        Button buttonOK = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        buttonOK.addEventFilter(javafx.event.ActionEvent.ACTION, event ->{
            if (!validateInput(document, inputs)){
                event.consume();
            }
        });

        //Especifica lo que returna la al presionar OK
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                LinkedHashMap<String,String> object = new LinkedHashMap<>();
                for (Attribute attribute : document.getAttributes()){
                    String inputValue = null;
                    if (!attribute.getType().equals("Date")){
                        TextField input = (TextField) inputs.get(attribute.getName());
                        inputValue = input.getText();
                    }else{
                        DatePicker input = (DatePicker) inputs.get(attribute.getName());
                        if(input.getValue() != null) {
                            inputValue = input.getValue().toString();
                        }
                    }
                    if(inputValue == null || inputValue.isEmpty()){
                        inputValue = attribute.getDefaultValue();
                    }
                    object.put(attribute.getName(), inputValue);
                }
                // Aqui debe anadir los objetos nuevos al documento
                document.addObject(object);
                return object;
            }
            return null;
        });

        dialog.getDialogPane().setContent(grid);
        return dialog.showAndWait();

    }

    private boolean validateInput(Document document, LinkedHashMap<String, Object> inputs){
        for(Attribute attribute : document.getAttributes()) {
            if (!attribute.getType().equals("Date")) {
                TextField input = (TextField) inputs.get(attribute.getName());
                String inputValue = input.getText();

                if (attribute.isRequired() && inputValue.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid entry");
                    alert.setHeaderText("Entry required");
                    alert.setContentText("One or more required fields are empty");

                    alert.showAndWait();
                    return false;
                }

                if (attribute.getSpecialKey().equals("Foreign key")){
                    if (!document.checkForeignKey(input.getText())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Foreign key doesn't exists");
                        alert.setContentText("The value of the foreign key must match an object at the foreign key origin");

                        alert.showAndWait();
                        return false;
                    }
                }else if(attribute.getSpecialKey().equals("Primary key")){
                    if(!document.searchObjectByAttribute(inputValue, document.searchPrimaryKey().getName()).isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Primary key already exists");
                        alert.setContentText("The value of the primary key must be unique");

                        alert.showAndWait();
                        return false;
                    }
                }

                if (attribute.getType().equals("Integer")) {
                    if (!inputValue.chars().allMatch(Character::isDigit)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Input isn't Integer");
                        alert.setContentText("Input must be of type Integer");

                        alert.showAndWait();
                        return false;
                    }

                }else if(attribute.getType().equals("Float")){
                    IntPredicate condition = new IntPredicate() {
                        @Override
                        public boolean test(int value) {
                            if (value == 46){
                                return true;
                            }else{
                                return false;
                            }
                        }
                    };

                    if (!inputValue.chars().allMatch(condition.or(Character::isDigit))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Input isn't Float");
                        alert.setContentText("Input must be of type Float");

                        alert.showAndWait();
                        return false;
                    }
                }
            }else {
                DatePicker input = (DatePicker) inputs.get(attribute.getName());
                if (attribute.isRequired() && input == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid entry");
                    alert.setHeaderText("Entry required");
                    alert.setContentText("One or more required fields are empty");

                    alert.showAndWait();
                    return false;
                }
            }
        }
        return true;
    }

    public Optional<HashMap<String, String>> showandupdate(Document document){

        //Crea el dialogo
        Dialog<HashMap<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Update Object");


        //Agrega los botones
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        //Crea el contenedor principal
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        // Crea campos para cada atributo
        int count = 0;
        LinkedHashMap<String, Object> inputs = new LinkedHashMap<>();
        for(Attribute attribute : document.getAttributes()){
            grid.add(new Label(attribute.getName()), 0, count);
            if (!attribute.getType().equals("Date")){
                TextField input = new TextField();
                input.setPromptText(attribute.getName());
                grid.add(input, 1, count);
                inputs.put(attribute.getName(), input);
            }else{
                DatePicker input = new DatePicker();
                grid.add(input, 1, count);
                inputs.put(attribute.getName(), input);
            }
            count++;
        }

        // Valida las entradas
        Button buttonOK = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        buttonOK.addEventFilter(javafx.event.ActionEvent.ACTION, event ->{
            if (!validateType(document, inputs)){
                event.consume();
            }
        });

        //Especifica lo que returna la al presionar OK
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                LinkedHashMap<String,String> object = new LinkedHashMap<>();
                for (Attribute attribute : document.getAttributes()){
                    String inputValue = null;
                    if (!attribute.getType().equals("Date")){
                        TextField input = (TextField) inputs.get(attribute.getName());
                        inputValue = input.getText();
                    }else{
                        DatePicker input = (DatePicker) inputs.get(attribute.getName());
                        if(input.getValue() != null) {
                            inputValue = input.getValue().toString();
                        }else{
                            inputValue = "";
                        }
                    }
                    object.put(attribute.getName(), inputValue);
                }
                // Aqui debe anadir los objetos nuevos al documento
                document.addObject(object);
                return object;
            }
            return null;
        });

        dialog.getDialogPane().setContent(grid);
        return dialog.showAndWait();

    }

    private boolean validateType(Document document, LinkedHashMap<String, Object> inputs){
        for(Attribute attribute : document.getAttributes()) {
            if (!attribute.getType().equals("Date")) {
                TextField input = (TextField) inputs.get(attribute.getName());
                String inputValue = input.getText();

                if (attribute.getSpecialKey().equals("Foreign key")){
                    if (!document.checkForeignKey(input.getText())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Foreign key doesn't exists");
                        alert.setContentText("The value of the foreign key must match an object at the foreign key origin");

                        alert.showAndWait();
                        return false;
                    }
                }

                if (attribute.getType().equals("Integer")) {
                    if (!inputValue.chars().allMatch(Character::isDigit)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Input isn't Integer");
                        alert.setContentText("Input must be of type Integer");

                        alert.showAndWait();
                        return false;
                    }

                }else if(attribute.getType().equals("Float")){
                    IntPredicate condition = new IntPredicate() {
                        @Override
                        public boolean test(int value) {
                            if (value == 46){
                                return true;
                            }else{
                                return false;
                            }
                        }
                    };

                    if (!inputValue.chars().allMatch(condition.or(Character::isDigit))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid input");
                        alert.setHeaderText("Input isn't Float");
                        alert.setContentText("Input must be of type Float");

                        alert.showAndWait();
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
