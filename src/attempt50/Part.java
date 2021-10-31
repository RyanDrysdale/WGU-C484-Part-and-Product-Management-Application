package attempt50;

/**
 * Class part.java
 * @author Ryan Drysdale 
 */
public abstract class Part {
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;
    public Part(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
        
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
        
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
        
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        
    }
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
        
    }
    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
        
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
        
    }
     /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
        
    }
    /**
     * @return the min
     */
    public int getMin() {
        return min;
        
    }
     /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
        
    }
    /**
     * @return the max
     */
    public int getMax() {
        return max;
        
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
        
    }
    
}
