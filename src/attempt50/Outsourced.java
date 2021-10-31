package attempt50;

/**
Outsourced Part

@author Ryan Drysdale
 */
public class Outsourced extends Part {
    /**
    
    The company name for the part
     
    */
    private String companyName;
    
    /**Constructor for Outsourced part
    @param id for the ID of the part
    @param name for the name of the part
    @param stock for the stock of the part
    @param price for the price of the part
    @param min for the minimum inventory level of the part
    @param max for the maximum inventory level of the part
    @param companyName for the company name for the part
    */
    public Outsourced(int id, String name, int stock, double price, int min, int max, String companyName) {
        super(id, name, stock, price, min, max);
        this.companyName = companyName;
    }
    /**Getter for companyName
     
    @return companyName
     
    */
    public String getCompanyName() {
        return companyName;
        
    }
    
    /**Setter for companyName
    @param companyName
    */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        
    }
    
}
