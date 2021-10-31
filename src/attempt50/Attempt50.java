package attempt50;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 The Inventory Management app manages an inventory of parts and products. 
 
 @author Ryan Drysdale
 */
public class Attempt50 extends Application {
    
    /**The start method loads the main screen/starting point of the application. 
    @param primaryStage
    @throws IOException
     */
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    /**
     
     The main method is the starting point of the application. 
     The main method loads sample data and launches the inventory management application
     
     @param args
     
     */
    public static void main(String[] args) {
        
        //This will load and display sample parts        
        int partId = Inventory.getNewPartId();
        InHouse examplePart1 = new InHouse(partId,"Example Part 1", 5, 199.99, 5, 20,
                101);
        partId = Inventory.getNewPartId();
        InHouse examplePart2 = new InHouse(partId,"Example Part 2", 5, 100.00, 5, 20,
                101);
        partId = Inventory.getNewPartId();
        InHouse examplePart3 = new InHouse(partId,"Example Part 3", 5, 5.99, 1, 20,
                101);
        partId = Inventory.getNewPartId();
        Outsourced examplePart4 = new Outsourced(partId, "Example Part 4",50, 5.99, 30,
                150, "Company Part Name");
        Inventory.addPart(examplePart1);
        Inventory.addPart(examplePart2);
        Inventory.addPart(examplePart3);
        Inventory.addPart(examplePart4);

        //This will load and display a sample product with associated parts
        int productId = Inventory.getNewProductId();
        Product exampleProduct1 = new Product(productId, "Example Product 1", 20, 499.99, 20,
                100);
        exampleProduct1.addAssociatedPart(examplePart1);
        exampleProduct1.addAssociatedPart(examplePart2);
        exampleProduct1.addAssociatedPart(examplePart3);
        exampleProduct1.addAssociatedPart(examplePart4);
        Inventory.addProduct(exampleProduct1);

        launch(args); 
       
    }   
}
