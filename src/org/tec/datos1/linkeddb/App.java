package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Esta clase se encarga de almacenar los valores principales de la aplicacion asi como de cargar todos los datos a memoria
 */
public class App {
    public static DoubleList database = new DoubleList<Store>();
    public static String path = "/Users/Jai/Desktop/Linked DB/";

    /**
     * Metodo encargado de cargar a memoria las lecturas de los archivo JSON
     * @param controller Define la instancia de la clase de control de la interfaz grafica
     */
    public static void start(Controller controller){
        ObjectMapper mapper = new ObjectMapper();
        JSONStore[] savedData = null;
        File metadata = new File(path + "metadata.json");
        if(metadata.exists()) {
            try {
                savedData = mapper.readValue(metadata, JSONStore[].class);
                for (JSONStore jsonStore : savedData) {
                    Store store = jsonStore.toStore();
                    database.append(store);
                    TreeItem<String> storeLeaf = controller.addStore(store);
                    CircularList<Document> documents = store.getDocuments();
                    for (Document document : documents){
                        controller.addDocument(storeLeaf, document);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
