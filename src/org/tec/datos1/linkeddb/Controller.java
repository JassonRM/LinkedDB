package org.tec.datos1.linkeddb;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.tec.datos1.linkeddb.App.database;

/**
 * Esta clase se encarga de unir los elementos de la interfaz de usuario con la logica
 */
public class Controller {
    private ObjectMapper mapper = new ObjectMapper();
    private ContextMenu contextMenu;
    private Document openedDocument;

    // TreeView
    @FXML
    private TreeView<String> tree;
    private TreeItem root = new TreeItem();

    // Boton commit\
    @FXML
    private Button commitButton;

    // Table
    @FXML
    private TableView<Map> table;

    //Search box
    @FXML
    private TextField searchBox;


    /**
     * Buscar un elemento por atributo
     */
    @FXML
    protected void search(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        if(openedDocument != null){
            Document document = openedDocument;
            String input = searchBox.getText();
            LinkedList<HashMap<String, String>> result = document.searchObject(input);
            for(HashMap<String, String> object : result){
                table.getSelectionModel().select(object);
            }
        }
    }

    /**
     * Borra un elemento por llave primaria
     */
    @FXML
    protected void deleteObject(ActionEvent event) {
        if(openedDocument != null){
            Document document = openedDocument;
            TextInputDialog dialog = new TextInputDialog(document.searchPrimaryKey().getName());
            ImageView image = new ImageView(this.getClass().getResource("media/delete.png").toString());
            image.setPreserveRatio(true);
            image.setFitHeight(64);
            dialog.setGraphic(image);
            dialog.setTitle("Delete object");
            dialog.setHeaderText("Delete object using the primary key");
            dialog.setContentText(document.searchPrimaryKey().getName() + " to delete:");

            Optional<String> id = dialog.showAndWait();

            if (id.isPresent()){
                HashMap<String, String> object = document.delete(id.get());
                if(object != null){
                    table.getItems().remove(object);
                    commitButton.setDisable(false);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid key");
                    alert.setHeaderText("Inexistent object");
                    alert.setContentText("The key entered doesn't exists in this document");

                    alert.showAndWait();
                }
            }
        }
    }

    public void deleteKey(KeyEvent event){
       if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
           String key = openedDocument.searchPrimaryKey().getName();
           Map<String, String> object = table.getSelectionModel().getSelectedItem();
           String id = object.get(key);
           openedDocument.delete(id);
           table.getItems().remove(object);
           commitButton.setDisable(false);
       }
    }

    /**
     * Borra todos los elementos del documento
     */
    @FXML
    protected void deleteAll(ActionEvent event) {
        if (!database.exists(tree.getSelectionModel().getSelectedItem().getValue())) {
            deleteAllObjects();
            commitButton.setDisable(false);
        }
    }

    /**
     * Actualiza los valores de los objetos seleccionados
     */
    @FXML
    protected void updateObjects(ActionEvent event) {
        ObservableList<Map> selectedItems = table.getSelectionModel().getSelectedItems();
        NewObjectDialog dialog = new NewObjectDialog();
        Optional<HashMap<String, String>> newObject = dialog.showandupdate(openedDocument);

        //Se encarga de recolectar los objetos seleccionados
        if (newObject.isPresent()){
            LinkedList<LinkedHashMap<String, String>> objects = new LinkedList<>();
            for(Map item : selectedItems){
                String id = (String) item.get(openedDocument.searchPrimaryKey().getName());
                objects.add(openedDocument.getObjects().get(id));

            }
            for(Attribute attribute : openedDocument.getAttributes()){
                if(!newObject.get().get(attribute.getName()).isEmpty()){
                    for(HashMap<String, String> object : objects){
                        object.replace(attribute.getName(), newObject.get().get(attribute.getName()));
                    }
                }
            }

        }
        table.refresh();
        commitButton.setDisable(false);
    }
    /**
     * Guarda todos los datos a JSON
     *
     * @param event Evento dado por un boton
     */
    @FXML
    protected void saveChanges(ActionEvent event) {
        try {
            mapper.writeValue(new File(App.path + "metadata.json"), App.database.toJSONArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object value : App.database) {
            Store store = (Store) value;
            store.saveDocuments();
        }

        commitButton.setDisable(true);
    }

    /**
     * Ejecuta las funciones al inicio del programa
     */
    @FXML
    private void initialize() {
        tree.setRoot(root);
        tree.setShowRoot(false);
        root.setExpanded(true);
        commitButton.setDisable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        App.start(this);
    }

    /**
     * Abre el dialogo para crear un nuevo store y lo agrega a la base de datos
     *
     * @param event Evento dado por un boton
     */
    @FXML
    protected void newStore(ActionEvent event) {
        //Crea el dialogo para el nombre
        TextInputDialog dialog = new TextInputDialog("Name");
        ImageView image = new ImageView(this.getClass().getResource("media/newStore.png").toString());
        image.setPreserveRatio(true);
        image.setFitHeight(64);
        dialog.setGraphic(image);
        dialog.setTitle("New Store");
        dialog.setHeaderText("Add new store to database");
        dialog.setContentText("Name for new store:");

        Optional<String> nombre = dialog.showAndWait();

        //Crea el store, lo agrega a la database
        if (nombre.isPresent()) {
            Store store = new Store(nombre.get());
            App.database.append(store);
            TreeItem<String> storeLeaf = new TreeItem<String>(store.getName());
            root.getChildren().add(storeLeaf);
            try {
                mapper.writeValue(new File(App.path + "metadata.json"), App.database.toJSONArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Abre el dialogo para crear un nuevo documento y lo anade al store seleccionado
     *
     * @param event Evento accionado por un boton
     */
    @FXML
    protected void newDocument(ActionEvent event) {
        TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
        if (item != null) {
            if (database.exists(item.getValue())) {
                NewDocumentDialog.newDialog(item.getValue(), tree, commitButton);
            }
        }
    }

    /**
     * Maneja las interacciones con el arbol de la base de datos y abre los menus contextuales correspondientes
     *
     * @param mouseEvent Recibe acciones del mouse
     */
    @FXML
    protected void treeMouseClicked(MouseEvent mouseEvent) {
        //Guarda la hoja seleccionada
        TreeItem<String> item = tree.getSelectionModel().getSelectedItem();

        //Cierra el menu contextual anterior
        if(this.contextMenu != null) {
            this.contextMenu.hide();
        }
        // Click derecho
        if (item != null) {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                // Crea menu
                ContextMenu menuAlt = new ContextMenu();
                if (item.getParent() == root) {
                    //Boton new document
                    MenuItem newDocument = new MenuItem("New Document");
                    newDocument.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            NewDocumentDialog.newDialog(item.getValue(), tree, commitButton);
                        }
                    });
                    menuAlt.getItems().addAll(newDocument);

                } else {

                    //Boton eliminar objetos
                    MenuItem deleteAll = new MenuItem("Delete all objects");
                    deleteAll.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            deleteAllObjects();
                        }
                    });
                    //Boton anadir objetos
                    MenuItem newObject = new MenuItem("Create new object");
                    newObject.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Document document = openedDocument;
                            NewObjectDialog dialog = new NewObjectDialog();
                            Optional<HashMap<String, String>> newObject = dialog.showandwait(document);
                            if (newObject.isPresent()) {
                                table.getItems().addAll(newObject.get());
                                commitButton.setDisable(false);
                            }
                        }
                    });
                    menuAlt.getItems().addAll(newObject, deleteAll);
                }
                setContextMenu(menuAlt, mouseEvent.getScreenX(), mouseEvent.getScreenY());

            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) { // Click izquierdo
                if (!(item.getParent() == root)) {
                    Store store = (Store) database.search(item.getParent().getValue());
                    Document document = (Document) store.getDocuments().search(item.getValue());
                    openedDocument = document;
                    table.getColumns().clear();
                    table.getItems().clear();
                    for (Attribute attribute : document.getAttributes()) {
                        TableColumn<Map, String> newColumn = new TableColumn<>(attribute.getName());
                        newColumn.setCellValueFactory(new MapValueFactory<>(attribute.getName()));
                        table.getColumns().add(newColumn);
                    }
                    for (Map.Entry entrada : document.getObjects().entrySet()) {
                        Map<String, String> objeto = (Map<String, String>) entrada.getValue();
                        table.getItems().addAll(objeto);
                    }
                }
            }
        }
    }


    /**
     * Cierra el programa
     *
     * @param event Evento de un boton
     */
    @FXML
    protected void close(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Muestra informacion acerca del software
     *
     * @param event Evento de un boton
     */
    @FXML
    protected void about(ActionEvent event) {

    }

    /**
     * Anade un store al arbol de la base de datos
     *
     * @param store Store para anadir
     * @return Devuelve una referencia a la hoja del arbol donde se anadio
     */
    public TreeItem<String> addStore(Store store) {
        TreeItem<String> storeLeaf = new TreeItem<String>(store.getName());
        root.getChildren().add(storeLeaf);
        return storeLeaf;
    }

    /**
     * Anade un documento al arbol de la base de datos
     *
     * @param store    Store al cual se va a anadir el documento
     * @param document Documento que se va a anadir
     */
    public void addDocument(TreeItem<String> store, Document document) {
        TreeItem<String> docLeaf = new TreeItem<String>(document.getName());
        store.getChildren().add(docLeaf);
        store.setExpanded(true);
    }

    /**
     * Elimina todos los objetos del documento
     */
    public void deleteAllObjects() {
        TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
        Store store = (Store) database.search(item.getParent().getValue());
        Document document = (Document) store.getDocuments().search(item.getValue());
        document.deleteAll();
        table.getItems().clear();
        commitButton.setDisable(false);
    }

    public void setContextMenu(ContextMenu contextMenu, double x, double y) {
        this.contextMenu = contextMenu;
        this.contextMenu.show(Main.getRootStage(), x, y);
    }
}
