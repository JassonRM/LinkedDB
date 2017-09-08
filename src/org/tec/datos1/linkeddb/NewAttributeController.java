package org.tec.datos1.linkeddb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class NewAttributeController {
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
        NewAttribute.cancel();
    }

    @FXML
    void okButton(ActionEvent event) {
        NewDocumentDialog.getController().newAttribute(nameTextfield.getText(), typeBox.getValue(), specialBox.getValue(), foreignTextfield.getText(), requiredCheckbox.isSelected(), defaultTextfield.getText());
        NewAttribute.cancel();
    }


    @FXML
    public void initialize() {
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

        foreignTextfield.setDisable(true);
    }


}
