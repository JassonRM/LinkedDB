package org.tec.datos1.linkeddb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Esta clase carga la ventana principal de la aplicacion
 */
public class Main extends Application {
    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }

    public static void setRootStage(Stage rootStage) {
        Main.rootStage = rootStage;
    }

    /**
     * Carga la ventana principal
     * @param primaryStage Stage principal
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LinkedDB.fxml"));
        setRootStage(primaryStage);
        primaryStage.setTitle("LinkedDB");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
