package attempt50;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**

Inventory of parts and products. 
@author Ryan Drysdale

 */

public class Inventory {
    /**ID for the part*/
    private static int partId = 0;
    /**ID for the product*/
    private static int productId = 0;
    /**a list of every part in the inventory*/
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**a list of every product in the inventory*/
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**Pulls a list of every part in the inventory
    @return all part objects
    */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**Pulls a list of every product in the inventory
    @return all product objects 
    */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /**Adds a part to the inventory
    @param newPart The part to add
    */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
        
    }
    /**Adds a product to the inventory
    @param newProduct The product to add 
    */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        
    }
    /**creates a new part ID
    @return a unique part ID 
    */
    public static int getNewPartId() {
        return ++partId;
        
    }
    /**creates a new product ID
    @return a unique product ID
    */
    public static int getNewProductId() {
        return ++productId;
        
    }
    /**Searches list by part ID
     @param partId The part ID
     @return The part if found, null if not
     */
    public static Part lookupPart(int partId) {
        Part partFound = null;
        
        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        
        return partFound;
    }
    
    /**Searches part list by name. 
     
    @param partName The part name. 
    @return A list of parts found.
    
    */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }

        return partsFound;
    }
    /**Looks up products from product list by ID
     
    @param productId the product Id
    @return The product if found, null if not found
     
    
    */
    public static Product lookupProduct(int productId) {
        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }

        return productFound;
    }
   /**Searches the product list by product name.
    
   @param productName The product name
   @return A list of products found
   
   */     
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }

        return productsFound;
    }
    /** Replaces a part in the part list
     
    @param index Index of the part being replaced. 
    @param selectedPart The part that is replacing. 
    
    */
    public static void updatePart (int index, Part selectedPart) {
        
        allParts.set(index, selectedPart);
        
    }
    /**Replaces a product in the product list. 
     
    @param index Index of the product getting replaced
    @param selectedProduct The product that is replacing it. 
    
    */
    public static void updateProduct (int index, Product selectedProduct) {
        
        allProducts.set(index, selectedProduct);
    }
    /**Removes a part from the part list
     
    @param selectedPart The part to be deleted
    @return A boolean confirming the removal.
    
    */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    /**Removes a product from the product list
     
    @param selectedProduct The product being removed. 
    @return A boolean confirming the removal. 
    
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
        
}    
    
    

