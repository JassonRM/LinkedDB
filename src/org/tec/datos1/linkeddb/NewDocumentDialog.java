package org.tec.datos1.linkeddb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;

public class NewDocumentDialog {
    private static Stage dialog = new Stage();

    public static void newDialog(String store, TreeView<String> treeView){
        Parent root;

        //Referencia el fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("NewDocumentDialog.fxml"));



        try {
            //Carga el fxml
            root = loader.load();

            //Asigna el nombre del store
            NewDocumentDialogController controller = loader.getController();
            controller.setNewDocLabel(store);
            controller.setTreeView(treeView);

            //Carga la ventana
            Scene scene = new Scene(root, 600, 400);
            dialog.setTitle("New Document");
            dialog.setScene(scene);
            dialog.setScene(scene);

            //Mantiene la ventana abierta
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void cancel(){
        dialog.close();
    }

}
