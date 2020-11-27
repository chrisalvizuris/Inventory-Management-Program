package model;

public class InHouse extends Part{

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /***
     * This gets Machine ID for specific part.
     * @return Returns the machine ID for part calling this method.
     */
    public int getMachineId() {
        return machineId;
    }

    /***
     * Sets the machine ID for a part.
     * @param machineId The Machine ID we're setting.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
