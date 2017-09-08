package org.tec.datos1.linkeddb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NewAttribute {
    private static Stage dialog;

    public static void newDialog(){
        Parent root;
        dialog = new Stage();
        dialog.initOwner(NewDocumentDialog.getDialog());
        dialog.initModality(Modality.WINDOW_MODAL);
        //Referencia el fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("NewAttribute.fxml"));


        try {
            //Carga el fxml
            root = loader.load();

            //Carga la ventana
            Scene scene = new Scene(root, 600, 400);
            dialog.setTitle("New Attribute");
            dialog.setScene(scene);
            dialog.setScene(scene);
            dialog.setResizable(false);

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
