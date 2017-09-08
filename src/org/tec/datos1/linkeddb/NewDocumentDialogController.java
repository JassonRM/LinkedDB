package org.tec.datos1.linkeddb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static org.tec.datos1.linkeddb.App.database;

public class NewDocumentDialogController {

    private TreeView<String> treeView;

    @FXML
    private Label newDocLabel;

    @FXML
    private TextField nameTextfield;

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

        if (nombre != null) {
            Document document = new Document(nombre);
            Store store = (Store) database.search(item.getValue());
            store.newDocument(document);

            TreeItem<String> docLeaf = new TreeItem<String>(document.getName());
            item.getChildren().add(docLeaf);
            item.setExpanded(true);
            NewDocumentDialog.cancel();
        }
    }

    @FXML
    void newAtributeButton(ActionEvent event) {
        NewAttribute.newDialog();
    }

    @FXML
    void editAtributeButton(ActionEvent event) {

    }

    @FXML
    void deleteAtributeButton(ActionEvent event) {

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

    public void newAttribute(String name, String type, String special, String foreign, boolean required, String defaultValue){
        System.out.println(name);
    }
}
