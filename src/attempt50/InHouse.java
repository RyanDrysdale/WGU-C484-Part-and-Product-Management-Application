package attempt50;

/**

in-house part.
@author Ryan Drysdale

 */

public class InHouse extends Part {
    /**
    
    machine ID for in-house part
     
     */
    
    private int machineId;
    
    /**
    
    Constructor for an InHouse object. 
    
    @param id for part id
    @param name for the part name
    @param stock for the part stock
    @param price for the part price
    @param min for the minimum level of the part
    @param max for the maximum level of the part
    @param machineId for the machine ID of the part
     
     */
    
    public InHouse(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, stock, price, min, max);
        this.machineId = machineId;
    }
    
    /**
    
    getter for machineId
    @return machineId of the part
    
    */
    
    public int getMachineId() {
        return machineId;
        
    }
    
    /**
     
    setter for machineId
    @param machineId the machineId of the part
     
     */
    
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
}
