package org.tec.datos1.linkeddb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

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

        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setGraphic(new ImageView(this.getClass().getResource("media/newStore.png").toString()));
        dialog.setTitle("New Store");
        dialog.setHeaderText("Add new store to database");
        dialog.setContentText("Name for new store:");

        Optional<String> nombre = dialog.showAndWait();

        if (nombre.isPresent()){
            Store store = new Store(nombre.get());
            Main.database.append(store);
            TreeItem<String> storeLeaf = new TreeItem<String>(store.getName());
            root.getChildren().add(storeLeaf);
        }
    }

    // Menu contextual para Store
    @FXML protected void storeContext(MouseEvent mouseEvent){
        System.out.println("Menu contextual");
    }

    // Boton close
    @FXML protected void close(ActionEvent event){

    }

    // Boton about
    @FXML protected void about(ActionEvent event){

    }
}
