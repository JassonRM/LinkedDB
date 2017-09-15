package org.tec.datos1.linkeddb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static org.tec.datos1.linkeddb.App.database;

public class NewDocumentDialogController {

    private TreeView<String> treeView;

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

    @FXML
    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        specialCol.setCellValueFactory(new PropertyValueFactory<>("specialKey"));
        foreignCol.setCellValueFactory(new PropertyValueFactory<>("foreignKey"));
        requiredCol.setCellValueFactory(new PropertyValueFactory<>("required"));
        defaultCol.setCellValueFactory(new PropertyValueFactory<>("defaultValue"));
    }

    @FXML
    void cancelButton(ActionEvent event) {
        NewDocumentDialog.cancel();
    }

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
            }
            else {
                Document document = new Document(nombre, attributes);
                Store store = (Store) database.search(item.getValue());
                store.newDocument(document);

                TreeItem<String> docLeaf = new TreeItem<String>(document.getName());
                item.getChildren().add(docLeaf);
                item.setExpanded(true);
                NewDocumentDialog.cancel();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid entry");
            alert.setHeaderText("Invalid name");
            alert.setContentText("Name is a required field");

            alert.showAndWait();
        }
    }

    @FXML
    void newAtributeButton(ActionEvent event) {
        NewAttribute dialog = new NewAttribute();
        Attribute attribute = dialog.showAndWait();
        attributes.addAll(attribute);
        attributeTable.setItems(attributes);
    }

    @FXML
    void editAtributeButton(ActionEvent event) {
        Attribute attribute = attributeTable.getSelectionModel().getSelectedItem();
        NewAttribute dialog = new NewAttribute();
        dialog.update(attribute);
        attributeTable.refresh();
    }

    @FXML
    void deleteAtributeButton(ActionEvent event) {
        Attribute attribute = attributeTable.getSelectionModel().getSelectedItem();
        attributes.remove(attribute);
        attributeTable.refresh();
    }

    public void setNewDocLabel(String store){
        this.newDocLabel.setText("Add new document to " + store);
    }

    public String getNameTextfield() {
        return nameTextfield.getText();
    }

    public void setTreeView(TreeView<String> treeView) {
        this.treeView = treeView;
    }

}
