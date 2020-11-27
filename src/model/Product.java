package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /***
     * Gets the ID of the product.
     * @return Returns the ID of product.
     */
    public int getId() {
        return id;
    }

    /***
     * Sets the ID of the product.
     * @param id The ID of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Gets the name of a product.
     * @return The name of product we're returning.
     */
    public String getName() {
        return name;
    }

    /***
     * Sets the name of a product.
     * @param name The name we want for our product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Gets the price of a product.
     * @return Returns the price of product.
     */
    public double getPrice() {
        return price;
    }

    /***
     * Sets the price of a product.
     * @param price The price we're setting.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /***
     * Gets the stock of a product.
     * @return Returns the stock value.
     */
    public int getStock() {
        return stock;
    }

    /***
     * Sets the stock for a product.
     * @param stock The stock value being set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /***
     * Gets the minimum value for stock of product.
     * @return Returns the minimum stock value.
     */
    public int getMin() {
        return min;
    }

    /***
     * Sets the minimum stock value for product.
     * @param min The minimum value of stock we're setting.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /***
     * Gets the maximum stock value for product.
     * @return Returns the maximum stock value.
     */
    public int getMax() {
        return max;
    }

    /***
     * Sets the maximum stock value for product.
     * @param max The maximum stock value for product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /***
     * Adds a part to a product's associated parts list.
     * @param part The part being added.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /***
     * Gets the associated parts for a product.
     * @return Returns the associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /***
     * Deletes an associated part from a product's associated parts list.
     * @param selectedAssociatedPart The part being deleted from associate part.
     * @return Returns false if there is an error.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
        }
        return false;
    }
}
