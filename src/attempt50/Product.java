package attempt50;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Class Product 
  @author Ryan Drysdale
 */

public class Product {
    /**product ID*/
    private int id;
    /**product name*/
    private String name;
    /** product stock*/
    private int stock;
    /** product price */
    private double price;
    /**product minimum*/
    private int min;
    /**product max*/
    private int max;
    //**List of associated parts for the product*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    /** Product Constructor
     * 
     * @param id for the product ID
     * @param name for the product Name
     * @param stock for the product Stock
     * @param price for the product Price
     * @param min for the product Min
     * @param max for the product Max
     * 
     */
    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }
    
    /**Getter for Id
     * 
     * @return id of the product
     */
    public int getId() {
        return id;
        
    }
    /**Setter for Id
     * 
     * @param id for the product id
     */
    public void setId(int id) {
        this.id = id;
        
    }
    /**Getter for the product name
     * 
     * @return name
     */
    public String getName() {
        return name;
        
    }
    /**Setter for product name
     * 
     * @param name The product name
     */
    public void setName(String name) {
        this.name = name;
        
    }
    /**Getter for product Stock
     * 
     * @return stock
     */
    public int getStock() {
        return stock;
        
    }
    /**Setter for product stock
     * 
     * @param stock The stock of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
        
    }
    /**Getter for product price
     * 
     * @return price
     */
    public double getPrice() {
        return price;
        
    }
    /**The Setter for product price
     * 
     * @param price The product Price
     */
    public void setPrice(double price) {
        this.price = price;
        
    }
    /**Getter for product min
     * 
     * @return min
     */
    public int getMin() {
        return min;
        
    }
    /**Setter for product min
     * 
     * @param min Product min
     */
    public void setMin(int min) {
        this.min = min;
        
    }
    /**Getter for product max
     * 
     * @return max
     */
    public int getMax() {
        return max;
        
    }
    /**Setter for product max
     * 
     * @param max for product max
     */
    public void setMax(int max) {
        this.max = max;
    
    }
    /**Adds an associated part to a product. 
     * 
     * @param part The part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
       
    }
    /** Deletes an associated part from a product
     * 
     * @param selectedAssociatedPart
     * @return a boolean confirming deletion
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }
    /**Pulls a list of associated parts for product
     * 
     * @return a list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
   
    }

