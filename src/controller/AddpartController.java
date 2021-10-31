package controller;

import attempt50.InHouse;
import attempt50.Inventory;
import attempt50.Outsourced;
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

/** Controller class for adding a part in the application
 * 
 * @author Ryan Drysdale
 */
public class AddpartController implements Initializable {
    /**The outsourced radio button*/
    @FXML
    private RadioButton outsourcedRadioButton;
    /** the part inventory text field */
    @FXML
    private TextField partInventoryText;
    /** the part id label */
    @FXML
    private Label partIdNameLabel;
    /** the part max text field */
    @FXML
    private TextField partMaxText;
    /** the in-house radio button */
    @FXML
    private RadioButton inHouseRadioButton;
    /** the radio buttons toggle group */
    @FXML
    private ToggleGroup tgPartType;
    /** the part id text field */
    @FXML
    private TextField partIdText;
    /** the part name text field */
    @FXML
    private TextField partNameText;
    /** the part price text field */
    @FXML
    private TextField partPriceText;
    /** the part machine id/company name text field */
    @FXML
    private TextField partIdNameText;
    /** the part min text field */
    @FXML
    private TextField partMinText;
    /** loads the main screen of the application 
    @param event Cancel button
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
    
    /**Various alert popup messages
    @param alertType Alert popups
    */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
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
                alert.setContentText("Inventory must be a number equal to or between Min and Max.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Name");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    /**Confirms stock is equal/between min and max
    @param min The minimum part value
    @param max The maximum part value
    @param stock The stock level of the part
    @return Boolean confirming valid input
    */
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }

        return isValid;
    }
    
    /** Sets toggle to machine ID
    @param event In-house radio button. */
   
    @FXML
    void inHouseRadioButtonAction(ActionEvent event) {

        partIdNameLabel.setText("Machine ID");
    }

    /**Sets toggle to Company Name
    @param event Outsourced radio button. */
    @FXML
    void outsourcedRadioButtonAction(ActionEvent event) {

        partIdNameLabel.setText("Company Name");
    }

    /**Adds new part and returns to main screen
     
    @param event Save button. 
    @thorws IOException
    
    */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (name.isEmpty()) {
                displayAlert(5);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineId = Integer.parseInt(partIdNameText.getText());
                            InHouse newInHousePart = new InHouse(id, name, stock, price, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
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
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        returnToMainScreen(event);
                    }
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**Loads main screen controller
    @param event Passed from parent 
    @throws IOException
    */
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

   /**Confirms min is greater than 0 and less than max
   @param min The minimum part value
   @param max The maximum part value
   @return Boolean Confirms valid input
   */
    private boolean minValid(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }

        return isValid;
    }



    /**Initializes controller and sets in house radio to true
     
    @param location Location used to resolve paths for root object or null if location is not known. 
    @param resources Resources used to localize root object
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inHouseRadioButton.setSelected(true);
    }
}