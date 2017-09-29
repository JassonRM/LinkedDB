package org.tec.datos1.linkeddb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase se encarga de cargar la ventana para un nuevo documento
 */
public class NewDocumentDialog {
    private static Stage dialog;
    private static NewDocumentDialogController controller;

    /**
     * @return El stage del dialogo
     */
    public static Stage getDialog() {
        return dialog;
    }

    /**
     * Crea la ventana nueva a partir del fxml
     * @param store Store al que se desea agregar el documento
     * @param treeView Arbol de la interfaz grafica
     * @param commitButton Boton de commit de la ventana principal
     */
    public static void newDialog(String store, TreeView<String> treeView, Button commitButton){
        NewAttributeController.resetKeys();

        Parent root;
        dialog = new Stage();
        dialog.initOwner(Main.getRootStage());
        dialog.initModality(Modality.WINDOW_MODAL);

        //Referencia el fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("NewDocumentDialog.fxml"));


        try {
            //Carga el fxml
            root = loader.load();

            //Asigna el nombre del store
            controller = loader.getController();
            controller.setNewDocLabel(store);
            controller.setTreeView(treeView);
            controller.setCommitButton(commitButton);

            //Carga la ventana
            Scene scene = new Scene(root, 600, 400);
            dialog.setTitle("New Document");
            dialog.setScene(scene);
            dialog.setScene(scene);
            dialog.setResizable(false);

            //Mantiene la ventana abierta
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra la ventana de dialogo
     */
    public static void cancel(){
        dialog.close();
        dialog = null;
    }

    /**
     * @return El controlador de la ventana
     */
    public static NewDocumentDialogController getController(){
        return controller;
    }

}
