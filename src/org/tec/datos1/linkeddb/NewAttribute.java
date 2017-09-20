package org.tec.datos1.linkeddb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase ejecuta la logica para la ventana de dialogo para la creacion de un nuevo atributo
 */
public class NewAttribute {
    private Stage dialog;
    private FXMLLoader loader;

    /**
     * Constructor que carga el archivo fxml y la ventana de dialogo
     */
    public NewAttribute(){
        Parent root;
        this.dialog = new Stage();
        this.dialog.initOwner(NewDocumentDialog.getDialog());
        this.dialog.initModality(Modality.WINDOW_MODAL);
        //Referencia el fxml
        loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("NewAttribute.fxml"));


        try {
            //Carga el fxml
            root = loader.load();

            //Carga la ventana
            Scene scene = new Scene(root, 600, 400);
            this.dialog.setTitle("Attribute");
            this.dialog.setScene(scene);
            this.dialog.setScene(scene);
            this.dialog.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra la ventana de dialogo y espera hasta que se cierre para recuperar las entradas
     * @return el resultado del metodo retrieve del controlador de la ventana
     */
    public Attribute showAndWait(){
        //Mantiene la ventana abierta
        this.dialog.showAndWait();
        NewAttributeController controller = loader.getController();
        return controller.retrieve();
    }

    /**
     * Carga los datos de un atributo para editarlos
     * @param attribute atributo a ser editado
     */
    public void update(Attribute attribute) {
        NewAttributeController controller = loader.getController();
        controller.load(attribute);
        this.dialog.showAndWait();
        controller.update(attribute);
    }
}
