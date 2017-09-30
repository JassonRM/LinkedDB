package org.tec.datos1.linkeddb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static org.tec.datos1.linkeddb.App.database;

/**
 * Esta clase se encarga de conectar la interfaz del dialogo para un nuevo documento con la logica
 *
 */
public class NewDocumentDialogController {

    private TreeView<String> treeView;

    private Button commitButton;

    private ObservableList<Attribute> attributes = FXCollections.observableArrayList();

    @FXML
    private TableView<Attribute> attributeTable;

    @FXML
    private TableColumn<Attribute, String> typeCol;

    @FXML
    private TableColumn<Attribute, String> specialCol;

    @FXML
    private TableColumn<Attribute, String> requiredCol;

    @FXML
    private TableColumn<Attribute, String> nameCol;

    @FXML
    private TableColumn<Attribute, String> foreignCol;

    @FXML
    private TableColumn<Attribute, String> defaultCol;

    @FXML
    private Label newDocLabel;

    @FXML
    private TextField nameTextfield;

    /**
     * Configura las columnas de la tabla luego de cargar la ventana
     */
    @FXML
    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        specialCol.setCellValueFactory(new PropertyValueFactory<>("specialKey"));
        foreignCol.setCellValueFactory(new PropertyValueFactory<>("foreignKey"));
        requiredCol.setCellValueFactory(new PropertyValueFactory<>("required"));
        defaultCol.setCellValueFactory(new PropertyValueFactory<>("defaultValue"));
    }

    /**
     * Ejecuta el metodo cancel del dialogo al presionar el boton cancel
     * @param event Evento al presionar el boton cancel
     */
    @FXML
    void cancelButton(ActionEvent event) {
        NewDocumentDialog.cancel();
    }

    /**
     * Valida las entradas del documento y lo anade al store
     * @param event Evento al presionar el boton Ok
     */
    @FXML
    void okButton(ActionEvent event) {
        //Recibe el item seleccionado
        TreeItem<String> item = this.treeView.getSelectionModel().getSelectedItem();

        //Crea el documento, lo agrega al store
        String nombre = this.getNameTextfield();

        if (!nombre.isEmpty()) {
            if (attributes.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid entry");
                alert.setHeaderText("Inexistent attributes");
                alert.setContentText("At least one attribute is required");

                alert.showAndWait();
            }else if(!NewAttributeController.isPrimaryAssigned()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid entry");
                alert.setHeaderText("Inexistent primary key");
                alert.setContentText("There must exist a primary key in the document");

                alert.showAndWait();
            } else {
                Document document = new Document(nombre, attributes.toArray(new Attribute[0]));
                Store store = (Store) database.search(item.getValue());
                store.newDocument(document);

                for(Attribute attribute : attributes){
                    if(attribute.getSpecialKey().equals("Foreign key")){
                        String[] address = attribute.getForeignKey().split("/");
                        Store foreignStore = (Store) App.database.search(address[0]);
                        Document foreignDocument = (Document) foreignStore.getDocuments().search(address[1]);
                        foreignDocument.addForeignKey(store.getName() + "/" + document.getName());
                    }
                }

                TreeItem<String> docLeaf = new TreeItem<String>(document.getName());
                item.getChildren().add(docLeaf);
                item.setExpanded(true);
                commitButton.setDisable(false);
                NewDocumentDialog.cancel();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid entry");
            alert.setHeaderText("Invalid name");
            alert.setContentText("Name is a required field");

            alert.showAndWait();
        }
    }

    /**
     * Crea una ventana nueva para nuevo atributo
     * @param event Evento al presionar boton de nuevo atributo
     */
    @FXML
    void newAtributeButton(ActionEvent event) {
        NewAttribute dialog = new NewAttribute();
        Attribute attribute = dialog.showAndWait();
        if (attribute != null) {
            attributes.addAll(attribute);
            attributeTable.setItems(attributes);
        }
    }

    /**
     * Crea una ventana nueva para editar el atributo
     * @param event Evento al presionar boton de edtar atributo
     */
    @FXML
    void editAtributeButton(ActionEvent event) {
        Attribute attribute = attributeTable.getSelectionModel().getSelectedItem();
        NewAttribute dialog = new NewAttribute();
        dialog.update(attribute);
        attributeTable.refresh();
    }

    /**
     * Elimina el atributo de la tabla y memoria
     * @param event Evento al presionar boton de eliminar atributo
     */
    @FXML
    void deleteAtributeButton(ActionEvent event) {
        Attribute attribute = attributeTable.getSelectionModel().getSelectedItem();
        attributes.remove(attribute);
        attributeTable.refresh();
    }

    /**
     * Establece el mensaje de Anadir nuevo documento al store seleccionado
     * @param store Store al cual se le va a anadir el documento
     */
    public void setNewDocLabel(String store){
        this.newDocLabel.setText("Add new document to " + store);
    }

    /**
     * @return Entrada del nombre del documento
     */
    public String getNameTextfield() {
        return nameTextfield.getText();
    }

    /**
     * @param treeView Establece el arbol de la interfaz al cual se va a anadir el documento
     */
    public void setTreeView(TreeView<String> treeView) {
        this.treeView = treeView;
    }

    /**
     * @param commitButton Establece el boton de commit de la interfaz general
     */
    public void setCommitButton(Button commitButton) {
        this.commitButton = commitButton;
    }
}
