package org.tec.datos1.linkeddb;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

public class NewObjectDialog {

    public Optional<HashMap<String, String>> showandwait(Document document){

        //Crea el dialogo
        Dialog<HashMap<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Object");


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

        //Especifica lo que returna la al presionar OK
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK){
                LinkedHashMap<String,String> object = new LinkedHashMap<>();
                for (Attribute attribute : document.getAttributes()){
                    String inputValue;
                    if (!attribute.getType().equals("Date")){
                        TextField input = (TextField) inputs.get(attribute.getName());
                        inputValue = input.getText();
                    }else{
                        DatePicker input = (DatePicker) inputs.get(attribute.getName());
                        inputValue = input.getValue().toString(); //Null pointer exception si input.getValue() == null
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

}
