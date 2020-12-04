package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /***
     * Adds a part to the all Parts list.
     * @param newPart The part we're adding to the list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /***
     * Adds a product to the all Products list.
     * @param newProduct The product we're adding to the list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /***
     * This looks for a part by part ID and returns that part if found.
     * @param partId The ID of the part we're searching for.
     * @return Returns part or null if not found.
     */
    public static Part lookupPart(int partId) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).id == partId) {
                return allParts.get(i);
            }
        }
        return null;
    }

    /***
     * This looks for a product by product ID and returns that product if found.
     * @param productId The ID of the product we're searching for.
     * @return Returns product or null if not found.
     */
    public static Product lookupProduct(int productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productId) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    /***
     * This looks up a part by the part name and returns any related parts.
     * @param partName The name to be searched in the inventory.
     * @return Returns a list of related parts that contain the searched input.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partList = FXCollections.observableArrayList();
        ObservableList<Part> allPartsList = Inventory.getAllParts();

        for (Part part : allPartsList) {
            if (part.getName().contains(partName)) {
                partList.add(part);
            }
        }
        return partList;
    }

    /***
     * This looks up a product by the product name and returns any related products.
     * @param productName The name to be searched in the Inventory.
     * @return Returns a list of related products that contain the searched input.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        ObservableList<Product> allProductList = Inventory.getAllProducts();

        for (Product product : allProductList) {
            if (product.getName().contains(productName)) {
                productList.add(product);
            }
        }
        return productList;
    }

    /***
     * Updates a part from the Inventory.
     * @param index The index of the object in the allParts list to be updated.
     * @param selectedPart What the index's item should be updated to.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /***
     * Updates a product from the Inventory.
     * @param index The index of object in the allProducts list to be updated.
     * @param newProduct What the index's item should be updated to.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /***
     * Deletes a part from the Inventory.
     * @param selectedPart The part to be deleted.
     * @return Returns false if there is no part.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
        }
        return false;
    }

    /***
     * Deletes a product from the Inventory.
     * @param selectedProduct The product to be deleted.
     * @return Returns false if there is no product.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
        }
        return false;
    }

    /***
     * Call this to return all parts.
     * @return Returns list of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /***
     * Call this to return all products
     * @return Returns list of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
