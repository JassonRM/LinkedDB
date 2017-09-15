package org.tec.datos1.linkeddb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewAttributeController {

    private boolean confirm;

    @FXML
    private TextField foreignTextfield;

    @FXML
    private TextField defaultTextfield;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> specialBox;

    @FXML
    private CheckBox requiredCheckbox;

    @FXML
    private TextField nameTextfield;

    @FXML
    void cancelButton(ActionEvent event) {
        Stage stage = (Stage) nameTextfield.getScene().getWindow();
        stage.close();
        confirm = false;
    }

    @FXML
    void okButton(ActionEvent event) {
        if (nameTextfield.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid entry");
            alert.setHeaderText("Invalid name");
            alert.setContentText("Name is a required field");

            alert.showAndWait();
        } else {
            if (specialBox.getValue() == "Foreign key" && !foreignTextfield.getText().isEmpty() || specialBox.getValue() != "Foreign key"){ // Hay que validar la llave foranea
                Stage stage = (Stage) nameTextfield.getScene().getWindow();
                stage.close();
                confirm = true;
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid entry");
                alert.setHeaderText("Invalid foreign key");
                alert.setContentText("The foreign key entry isn't valid, please try again");

                alert.showAndWait();
            }
        }

    }


    @FXML
    public void initialize() {
        confirm = false;

        typeBox.getItems().addAll("Integer", "Float", "String", "Date");
        typeBox.getSelectionModel().select("Integer");

        specialBox.getItems().addAll("None", "Primary key", "Foreign key");
        specialBox.getSelectionModel().select("None");

        specialBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(specialBox.getValue() == "Foreign key"){
                    foreignTextfield.setDisable(false);
                }else{
                    foreignTextfield.setDisable(true);
                }
            }
        });

        requiredCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (requiredCheckbox.isSelected()) {
                    defaultTextfield.setDisable(true);
                }
                else{
                    defaultTextfield.setDisable(false);
                }
            }
        });

        foreignTextfield.setDisable(true);

    }

    public Attribute retrieve(){
        if(confirm) {
            return new Attribute(nameTextfield.getText(), typeBox.getValue(), specialBox.getValue(), foreignTextfield.getText(), requiredCheckbox.isSelected(), defaultTextfield.getText());
        }else {
            return null;
        }
    }

    public void load(Attribute attribute){
        nameTextfield.setText(attribute.getName());
        typeBox.getSelectionModel().select(attribute.getType());
        specialBox.getSelectionModel().select(attribute.getSpecialKey());
        foreignTextfield.setText(attribute.getForeignKey());
        requiredCheckbox.setSelected(attribute.isRequired());
        defaultTextfield.setText(attribute.getDefaultValue());
    }

    public void update(Attribute attribute){
        attribute.setName(nameTextfield.getText());
        attribute.setType(typeBox.getValue());
        attribute.setSpecialKey(specialBox.getValue());
        attribute.setForeignKey(foreignTextfield.getText());
        attribute.setRequired(requiredCheckbox.isSelected());
        attribute.setDefaultValue(defaultTextfield.getText());
    }


}
