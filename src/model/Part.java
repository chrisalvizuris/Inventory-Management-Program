package model;

public abstract class Part {

    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /***
     * Gets the ID of a part.
     * @return Returns the ID for the part.
     */
    public int getId() {
        return id;
    }

    /***
     * Sets the ID of a part.
     * @param id The ID of the part.
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Gets the name of a part.
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /***
     * Sets the name of a part.
     * @param name The name of the part.
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Gets the price of a part.
     * @return Returns the price.
     */
    public double getPrice() {
        return price;
    }

    /***
     * Sets the price of a part.
     * @param price The price of the part we're setting.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /***
     * Gets the stock value of a part.
     * @return Returns the stock.
     */
    public int getStock() {
        return stock;
    }

    /***
     * Sets the stock number for this part.
     * @param stock Stock value.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /***
     * Gets the minimum stock value for a specific part.
     * @return Returns the minimum value.
     */
    public int getMin() {
        return min;
    }

    /***
     * Sets the minimum stock for part.
     * @param min The minimum value of stock.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /***
     * Gets the maximum stock for part calling this method.
     * @return Returns the maximum stock value.
     */
    public int getMax() {
        return max;
    }

    /***
     * Sets the max stock for a part.
     * @param max The maximum value for stock.
     */
    public void setMax(int max) {
        this.max = max;
    }
}
