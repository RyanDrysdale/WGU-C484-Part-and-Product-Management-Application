package controller;

import attempt50.Inventory;
import attempt50.Part;
import attempt50.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
Class for the modify product screen
@author Ryan Drysdale
*/

public class ModifyproductController implements Initializable {

    /**Selected product on main screen controller*/
    Product selectedProduct;
    /**a list of a products associated parts*/
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    /**The associated part table view*/
    @FXML
    private TableView<Part> assocPartTableView;
    /**The associated part ID table column*/
    @FXML
    private TableColumn<Part, Integer> assocPartIdColumn;
    /**The associated part name table column*/
    @FXML
    private TableColumn<Part, String> assocPartNameColumn;
    /**The associated part stock table column*/
    @FXML
    private TableColumn<Part, Integer> assocPartInventoryColumn;
    /**The associated parts price table column*/
    @FXML
    private TableColumn<Part, Double> assocPartPriceColumn;

    /**The part table view*/
    @FXML
    private TableView<Part> partTableView;
    /**The part id table column*/
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    /**The part name table column*/
    @FXML
    private TableColumn<Part, String> partNameColumn;
    /**The part stock table column*/
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    /**The part price table column*/
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    /**The part search text field*/
    @FXML
    private TextField partSearchText;

    /**The product ID text field*/
    @FXML
    private TextField productIdText;

    /**The product name text field*/
    @FXML
    private TextField productNameText;

    /**The product stock text field*/
    @FXML
    private TextField productInventoryText;

    /**The product price text field*/
    @FXML
    private TextField productPriceText;

    /**The product max text field*/
    @FXML
    private TextField productMaxText;

    /**the product min text field*/
    @FXML
    private TextField productMinText;

    /**
    Adds part selected to the associated parts table
    @param event Add button action
    */
    
    @FXML
    void addButtonAction(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }
    }

    /**
    loads main screen controller
    @param event Cancel button action
    @throws IOException
    */
    
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("You are about to cancel. Continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
    removes selected part from associated parts table
    @param event Remove button action. 
    */
    
    @FXML
    void removeButtonAction(ActionEvent event) {

        Part selectedPart = assocPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("You are about to remove selected part. Continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartTableView.setItems(assocParts);
            }
        }
    }

    /**
    loads main screen controller. 
    @param event save button action.
    @throws IOException
    */
    
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
            String name = productNameText.getText();
            Double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInventoryText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if (name.isEmpty()) {
                displayAlert(7);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    Product newProduct = new Product(id, name, stock, price, min, max);

                    for (Part part : assocParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }

    /**
    searches parts table view based on user input in the parts search text field
    @param event Part search button action
    */
    
    @FXML
    void searchBtnAction(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partTableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(1);
        }
    }
    
    /**
    Shows all parts in part table view when search text field is empty
    @param event Parts search text field key pressed
    */
    
    @FXML
    void searchKeyPressed(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
    Loads main screen controller. 
    @param event Passed from parent method. 
    @throws IOException. 
    */
    
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
    Confirms min is greater than 0 and less than max. 
    @param min The minimum part value. 
    @param max The maximum part value. 
    @return Boolean indicating if min is valid. 
    */
    
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }

        return isValid;
    }
    
    /**
    Confirms inventory level is equal/between min and max. 
    @param min The minimum part value. 
    @param max The maximum part value. 
    @param stock The stock part value. 
    @return Boolean indicating if inventory is valid.
    */
    
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }

        return isValid;
    }
    
    /**
    Various alert popups. 
    @param alertType Alert message selector.
    */
    
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("Form contains invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("Name");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    /**
    Initializes controller and loads text fields with product selected in main screen controller. 
    @param location The location used to resolve relative paths for the root object, or null if unknown. 
    @param resources The resources used to localize the root object.
    */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedProduct = MainscreenController.getProductModified();
        assocParts = selectedProduct.getAllAssociatedParts();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartTableView.setItems(assocParts);

        productIdText.setText(String.valueOf(selectedProduct.getId()));
        productNameText.setText(selectedProduct.getName());
        productInventoryText.setText(String.valueOf(selectedProduct.getStock()));
        productPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxText.setText(String.valueOf(selectedProduct.getMax()));
        productMinText.setText(String.valueOf(selectedProduct.getMin()));
    }
}