package controller;

import attempt50.Inventory;
import attempt50.Part;
import attempt50.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Controller class for the main screen of the application.
 *
 * @author Ryan Drysdale
 */
public class MainscreenController implements Initializable {
    
    /**Part selected in the part table view*/
    private static Part partModified;
    /**Product selected in the product table view*/
    private static Product productModified;
    
    /**The part search text field*/
    @FXML private TextField partSearch;
    /**The part table view*/
    @FXML private TableView<Part> partTableView;
    /**The part ID table column in the part table view*/
    @FXML private TableColumn<Part, Integer> partIdColumn;
    /**the part name table column in the part table view*/
    @FXML private TableColumn<Part, String> partNameColumn;
    /**The part stock table column in the part table view*/
    @FXML private TableColumn<Part, Integer> partStockColumn;
    /**the part price table column in the part table view*/
    @FXML private TableColumn<Part, Double> partPriceColumn;
    
    /**the product search text field*/
    @FXML private TextField productSearch;
    /**The product table view*/
    @FXML private TableView<Product> productTableView;
    /**The product ID table column in the product table view*/
    @FXML private TableColumn<Product, Integer> productIdColumn;
    /**The product name table column in the product table view*/
    @FXML private TableColumn<Product, String> productNameColumn;
    /**The product stock table column in the product table view*/
    @FXML private TableColumn<Product, Integer> productStockColumn;
    /**The product price table column in the product table view*/
    @FXML private TableColumn<Product, Double> productPriceColumn;
    
    /**Pulls the part selected in the part table. 
    @return a Part, null if no part is selected*/
    public static Part getPartModified() {
        return partModified;
    }
    /**Pulls the product selected in the product table. 
    @return a product, null if no product is selected*/
    public static Product getProductModified() {
        return productModified;
    }
    
    /**
    Loads modify product controller
    @param event Product modify action
    @throws IOException 
    */
    
    @FXML
    void productModifyAction(ActionEvent event) throws IOException {

        productModified = productTableView.getSelectionModel().getSelectedItem();

        if (productModified == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/modifyproduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
    Loads modify part controller
    @param event part modify action
    @throws IOException
    */
    
    @FXML
    void partModifyAction(ActionEvent event) throws IOException {

        partModified = partTableView.getSelectionModel().getSelectedItem();

     
        if (partModified == null) {
            displayAlert(3);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/modifypart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
    Deletes product selected from product table
    @param event product delete action
    */
    
    @FXML
    void productDeleteAction(ActionEvent event) {

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setContentText("You are about to delete selected product. Continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

                if (assocParts.size() >= 1) {
                    displayAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }
    
    /**
    Deletes part from part table
    @param event part delete action
    */
    
    @FXML
    void partDeleteAction(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setContentText("You are about to delete selected part. Continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }
    
    /**
    Loads add part controller
    @param event Add button action
    @throws IOException
    */
    
    @FXML
    void partAddAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/addpart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
    Loads add product controller
    @param event Add product action
    @thorws IOException
    */
    
    @FXML
    void productAddAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/addproduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
    closes application
    @param event Exit button action
    */
    
    @FXML
    void exitButtonAction(ActionEvent event) {

        System.exit(0);
    }
    
    /**
    Searched based on user input in product search text field
    @param event Part search button action*/
    
    @FXML
    void productSearchAction(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        productTableView.setItems(productsFound);

        if (productsFound.size() == 0) {
            displayAlert(2);
        }
    }
    
    /**
    Refreshes product table view
    @param event Products search text key pressed. 
    */
    
    @FXML
    void productSearchTextKeyPressed(KeyEvent event) {

        if (productSearch.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
    Searches based on input from user into part search text field
    @param event Part search action
    */
    
    @FXML
    void partSearchAction(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearch.getText();

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
    Varies alert popups
    @param alertType Alert message selector. 
    */
    
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }

    /**
    initializes controller and populates table views. 
    @param location The location used to resolve relative paths for the root object, or null if unknown. 
    @param resources The resources used to localize the root object.  
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        
        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        
    }  
    
    
}
