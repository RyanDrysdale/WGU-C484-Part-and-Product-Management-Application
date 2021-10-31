package controller;

import attempt50.Inventory;
import attempt50.Part;
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
import attempt50.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**Controller class for adding a product
 
 @author Ryan Drysdale
*/
public class AddproductController implements Initializable {

    /**A list containing associated product parts */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**Associated parts table view*/
    @FXML
    private TableView<Part> assocPartTableView;

    /**Associated part ID table column*/
    @FXML
    private TableColumn<Part, Integer> assocPartIdColumn;

    /**Associated parts name table column*/
    @FXML
    private TableColumn<Part, String> assocPartNameColumn;

    /**Associated part stock table column*/
    @FXML
    private TableColumn<Part, Integer> assocPartInventoryColumn;

    /**Associated part price table column*/
    @FXML
    private TableColumn<Part, Double> assocPartPriceColumn;

    /**The part table view*/
    @FXML
    private TableView<Part> partTableView;

    /**The part Id table column*/
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**The part Name table column*/
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

    /**the product Id text field*/
    @FXML
    private TextField productIdText;

    /**the product name text field*/
    @FXML
    private TextField productNameText;

    /**the product stock text field*/
    @FXML
    private TextField productInventoryText;

    /**The product price text field*/
    @FXML
    private TextField productPriceText;

    /**The product max text field*/
    @FXML
    private TextField productMaxText;

    /**The product min text field*/
    @FXML
    private TextField productMinText;
    
    /**
    Various alert pop ups
    @param alertType Alert message selector.
    */
    
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
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
    Adds new product to inventory and returns to main screen. 
    @param event Save button. 
    @throws IOException
    */
    
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = 0;
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

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }
    
    /**
    Adds part selected in all parts table view to the associated parts table view
    @param event Add button. 
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
    Loads main screen controller
    @param event Cancel button.
    @throws IOException
    */
    
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
    Initiates a search based on value entered into the part search text field
    @param event Part search button. 
    */
    
    @FXML
    void partSearchBtnAction(ActionEvent event) {

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
    Refreshes part table view
    @param event Parts search key pressed
    */
    
    @FXML
    void partSearchKeyPressed(KeyEvent event) {

        if (partSearchText.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
    Removes selected part from associated parts table
    @param event Remove button.
    */
    
    @FXML
    void removeButtonAction(ActionEvent event) {

        Part selectedPart = assocPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartTableView.setItems(assocParts);
            }
        }
    }


    /**
    Loads main screen controller
    @Param event Passed from parent method.
    @throws IOException
    */
    
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** 
    Confirms min is greater than 0 and less than max
    @param min The minimum value of the part. 
    @param max The maximum value of the part.
    @return Boolean confirming min is valid
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
    Confirms stock level is equal to/between min and max
    @param min The minimum value of the part. 
    @param max The maximum value of the part. 
    @param stock The stock level for the part. 
    @return Boolean confirming inventory is valid.
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
    populates table views. 
    @param location The location used to resolve relative paths for the root object, or null if unknown
    @param resources The resources used to localize the root object
    */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}