package controller;

import attempt50.InHouse;
import attempt50.Inventory;
import attempt50.Outsourced;
import attempt50.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
Class for the modify part screen of the application
@author Ryan Drysdale
*/

public class ModifypartController implements Initializable {
    
    /**The outsourced radio button*/
    @FXML
    private RadioButton outsourcedRadioButton;
    /**The part selected in main screen controller*/
    private Part selectedPart;
    /**The part max text field*/
    @FXML
    private TextField partMaxText;
    /**The machineID/companyName label*/
    @FXML
    private Label partIdNameLabel;
    /**The in-house radio button*/
    @FXML
    private RadioButton inHouseRadioButton;
    /**the toggle group for the radio buttons*/
    @FXML
    private ToggleGroup tgPartType;
    /**the part ID text field*/   
    @FXML
    private TextField partIdText;
    /**The part name text field*/
    @FXML
    private TextField partNameText;
    /**The part inventory text field*/
    @FXML
    private TextField partInventoryText;
    /**The part price text field*/
    @FXML
    private TextField partPriceText;
    /**the part CompanyNmae/MachineID text field*/
    @FXML
    private TextField partIdNameText;
    /**the min part text field*/
    @FXML
    private TextField partMinText;
    
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
    Sets machine/company name label to Machine ID. 
    @param event In-house radio button action
    */
    
    @FXML
    void inHouseRadioButtonAction(ActionEvent event) {

        partIdNameLabel.setText("Machine ID");
    }

    /**
    Sets machine/company name to Company Name
    @param event Outsourced radio button
    */
    
    @FXML
    void outsourcedRadioButtonAction(ActionEvent event) {

        partIdNameLabel.setText("Company Name");
    }

    /**
    replaced part and loads main screen controller
    @param event Save button action
    @throws IOException
    */
    
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {

                if (inHouseRadioButton.isSelected()) {
                    try {
                        machineId = Integer.parseInt(partIdNameText.getText());
                        InHouse newInHousePart = new InHouse(id, name, stock, price, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAddSuccessful = true;
                    } catch (Exception e) {
                        displayAlert(2);
                    }
                }

                if (outsourcedRadioButton.isSelected()) {
                    companyName = partIdNameText.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, stock, price, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }

                if (partAddSuccessful) {
                    Inventory.deletePart(selectedPart);
                    returnToMainScreen(event);
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**
    loads main screen controller
    @param event Passed from parent method.
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
    Confirms min is greater than 0 and less than max. 
    @param min The minimum part value. 
    @param max The maximum part value. 
    @return Boolean confirming valid min
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
   Confirms inventory level is equal/between mid and max. 
   @param min The minimum part value. 
   @param max The maximum part value. 
   @param stock The inventory for the part
   @return Boolean confirming valid inventory
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
    Various pop up alerts
    @param alertType Alert message selector
    */
    
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("Form contains invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Machine ID");
                alert.setContentText("Machine ID may only contain numbers.");
                alert.showAndWait();
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
        }
    }

    /**
    Initializes controller
    @param location The location used to resolve relative paths for the root object, or null if unknown. 
    @param resources The resources used to localize the root object. 
    */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedPart = MainscreenController.getPartModified();

        if (selectedPart instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            partIdNameLabel.setText("Machine ID");
            partIdNameText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced){
            outsourcedRadioButton.setSelected(true);
            partIdNameLabel.setText("Company Name");
            partIdNameText.setText(((Outsourced) selectedPart).getCompanyName());
        }

        partIdText.setText(String.valueOf(selectedPart.getId()));
        partNameText.setText(selectedPart.getName());
        partInventoryText.setText(String.valueOf(selectedPart.getStock()));
        partPriceText.setText(String.valueOf(selectedPart.getPrice()));
        partMaxText.setText(String.valueOf(selectedPart.getMax()));
        partMinText.setText(String.valueOf(selectedPart.getMin()));
    }
}
