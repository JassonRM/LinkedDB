package org.tec.datos1.linkeddb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App {
    public static DoubleList database = new DoubleList<Store>();

    public static void start(){
    }



    public static void newAtributeDialog(){
        //Carga el fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("NewAtributeDialog.fxml"));

        try {
            //Crea la nueva ventana
            Scene scene = new Scene(loader.load(), 600, 400);
            Stage dialog = new Stage();
            dialog.setTitle("New Atribute");
            dialog.setScene(scene);
            dialog.setScene(scene);

            //Mantiene la ventana abierta
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
