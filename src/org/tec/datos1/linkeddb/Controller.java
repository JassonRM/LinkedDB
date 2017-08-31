package org.tec.datos1.linkeddb;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

import static org.tec.datos1.linkeddb.App.database;

public class Controller {

    // TreeView
    @FXML private TreeView<String> tree;
    private TreeItem root = new TreeItem();

    // Boton commit\
    @FXML private Button commitButton;

    @FXML protected void saveChanges(ActionEvent event){

    }

    // Boton New store
    @FXML protected void newStore(ActionEvent event){

        if(tree.getRoot() == null){
            tree.setRoot(root);
            tree.setShowRoot(false);
            root.setExpanded(true);
        }

        //Crea el dialogo para el nombre
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setGraphic(new ImageView(this.getClass().getResource("media/newStore.png").toString()));
        dialog.setTitle("New Store");
        dialog.setHeaderText("Add new store to database");
        dialog.setContentText("Name for new store:");

        Optional<String> nombre = dialog.showAndWait();

        //Crea el store, lo agrega a la database
        if (nombre.isPresent()){
            Store store = new Store(nombre.get());
            App.database.append(store);
            TreeItem<String> storeLeaf = new TreeItem<String>(store.getName());
            root.getChildren().add(storeLeaf);

        }
    }

    // Menu contextual treeview
    @FXML protected void storeContext(MouseEvent mouseEvent){

        //Guarda la hoja seleccionada
        TreeItem<String> item = tree.getSelectionModel().getSelectedItem();

        // Click derecho
        System.out.println(database.exists(item.getValue()));
        if(mouseEvent.getButton() == MouseButton.SECONDARY) {
            // Crea menu
            ContextMenu menuAlt = new ContextMenu();
            if(database.exists(item.getValue())){
                //Boton new document
                MenuItem newDocument = new MenuItem("New Document");
                newDocument.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Nuevo documento en " + item.getValue());
                        TextInputDialog dialog = new TextInputDialog("Name");
                        dialog.setGraphic(new ImageView(this.getClass().getResource("media/newStore.png").toString()));
                        dialog.setTitle("New Document");
                        dialog.setHeaderText("Add new document to " + item.getValue());
                        dialog.setContentText("Name for new document:");

                        Optional<String> nombre = dialog.showAndWait();

                        //Crea el documento, lo agrega al store
                        if (nombre.isPresent()) {
                            Document document = new Document(nombre.get());
                            Store store = (Store) database.search(item.getValue());
                            store.newDocument(document);

                            TreeItem<String> docLeaf = new TreeItem<String>(document.getName());
                            item.getChildren().add(docLeaf);

                        }
                    }
                });
                menuAlt.getItems().addAll(newDocument);

            }
            else{
                //Boton eliminar objetos
                MenuItem deleteAll = new MenuItem("Delete all objects");
                deleteAll.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //Elimina todos los objetos del documento
                        Store store = (Store) database.search(item.getParent().getValue());
                        Document document = (Document) store.getDocuments().search(item.getValue());

                    }
                });
            }
            menuAlt.show(tree, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
    }


    // Boton close
    @FXML protected void close(ActionEvent event){
        System.exit(0);
    }

    // Boton about
    @FXML protected void about(ActionEvent event){

    }
}
