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

/**
 * Esta clase se encarga de unir los elementos de la interfaz de la ventana de nuevo atributo con la logica
 */
public class NewAttributeController {
    private static boolean primaryAssigned = false;
    private static boolean foreignAssigned = false;

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

    /**
     * Se ejecuta al presionar el boton de cancelar y cierra la ventana
     * @param event Evento al presionar boton cancelar
     */
    @FXML
    void cancelButton(ActionEvent event) {
        Stage stage = (Stage) nameTextfield.getScene().getWindow();
        stage.close();
        confirm = false;
    }

    /**
     * Valida todas las entradas y cierra la ventana
     * @param event Evento al presionar boton Ok
     */
    @FXML
    void okButton(ActionEvent event) {

        if (nameTextfield.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid entry");
            alert.setHeaderText("Invalid name");
            alert.setContentText("Name is a required field");

            alert.showAndWait();
        } else {
            if(specialBox.getValue().equals("Primary key")){
                if (primaryAssigned){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid entry");
                    alert.setHeaderText("Primary key exists");
                    alert.setContentText("There is already a primary key assigned to this document");

                    alert.showAndWait();
                } else {
                    Stage stage = (Stage) nameTextfield.getScene().getWindow();
                    stage.close();
                    primaryAssigned = true;
                    confirm = true;
                }
            } else if (specialBox.getValue().equals("Foreign key")){
                if (foreignTextfield.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid entry");
                    alert.setHeaderText("Invalid foreign key");
                    alert.setContentText("The foreign key entry isn't valid, please try again");

                    alert.showAndWait();
                } else if (foreignAssigned){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid entry");
                    alert.setHeaderText("Foreign key exists");
                    alert.setContentText("There is already a foreign key assigned to this document");

                    alert.showAndWait();
                } else{
                    String[] address = foreignTextfield.getText().split("/");
                    if(App.database.exists(address[0])){ /// No entraaaaa
                        Store store = (Store) App.database.search(address[0]);
                        if(store.getDocuments().search(address[1]) != null) {
                            Document document = (Document) store.getDocuments().search(address[1]);
                            if (document.searchAttribute(address[2]) != null){
                                Stage stage = (Stage) nameTextfield.getScene().getWindow();
                                stage.close();
                                foreignAssigned = true;
                                confirm = true;
                            } else {
                                invalidAddress();
                            }
                        } else{
                            invalidAddress();
                        }
                    }else {
                        invalidAddress();
                    }
                }
            }
            else {
                Stage stage = (Stage) nameTextfield.getScene().getWindow();
                stage.close();
                confirm = true;
            }
        }

    }

    /**
     * Metodo que se ejecuta luego de cargar el controlador, asigna los valores predeterminados
     */
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
                    foreignTextfield.setPromptText("Store/Document/Attribute");
                }else{
                    foreignTextfield.setDisable(true);
                    foreignTextfield.setPromptText("");
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

    /**
     * Se encarga de retornar un atributo con los valores de las entradas una vez estas han sido validadas
     * @return Atributo con los valores ingresados
     */
    public Attribute retrieve(){
        if(confirm) {
            return new Attribute(nameTextfield.getText(), typeBox.getValue(), specialBox.getValue(), foreignTextfield.getText(), requiredCheckbox.isSelected(), defaultTextfield.getText());
        }else {
            return null;
        }
    }

    /**
     * Carga los atributos para su edicion
     * @param attribute El atributo a editar
     */
    public void load(Attribute attribute){
        nameTextfield.setText(attribute.getName());
        typeBox.getSelectionModel().select(attribute.getType());
        specialBox.getSelectionModel().select(attribute.getSpecialKey());
        foreignTextfield.setText(attribute.getForeignKey());
        requiredCheckbox.setSelected(attribute.isRequired());
        defaultTextfield.setText(attribute.getDefaultValue());
    }

    /**
     * Guarda los cambios del atributo en edicion
     * @param attribute Atributo a editar
     */
    public void update(Attribute attribute){
        attribute.setName(nameTextfield.getText());
        attribute.setType(typeBox.getValue());
        attribute.setSpecialKey(specialBox.getValue());
        attribute.setForeignKey(foreignTextfield.getText());
        attribute.setRequired(requiredCheckbox.isSelected());
        attribute.setDefaultValue(defaultTextfield.getText());
    }

    /**
     * Crea una alerta de error si la direccion de la llave foranea es invalida
     */
    public void invalidAddress(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid entry");
        alert.setHeaderText("Invalid foreign key");
        alert.setContentText("The foreign key assigned doesn't exist");

        alert.showAndWait();
    }

    public static void resetKeys(){
        primaryAssigned = false;
        foreignAssigned = false;
    }
}
