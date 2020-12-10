package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.util.Optional;

public class mainFormController {

    private ObservableList<InHouse> inHouseItems = FXCollections.observableArrayList();

    private ObservableList<Product> productItems = FXCollections.observableArrayList();

    @FXML
    private Button productSearchButton;

    @FXML
    private Button partSearchButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField partTextBox;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partStockColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TextField productTextBox;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productStockColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    /***
     * Closes the application
     * @param exitEvent Variable for onAction event to close application
     */
    @FXML
    public void handleExitButtonAction(ActionEvent exitEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /***
     * Filters the Product Table View for Product name or ID
     * @param searchEvent Variable for Action Event
     * @throws IOException This method throws an IOException if method doesn't work
     */
    @FXML
    public void productSearchBoxSelected(ActionEvent searchEvent) throws IOException {
        try {
            String input = productTextBox.getText();

            ObservableList<Product> products = Inventory.lookupProduct(input);

            if (products.size() == 0) {
                int productID = Integer.parseInt(input);
                Product product = Inventory.lookupProduct(productID);
                if (product != null) {
                    products.add(product);
                }
            }
            productsTableView.setItems(products);
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: We can not find a product that matches your query.");
            alert.showAndWait();
            return;
        }
    }

    /***
     * Filters the Parts Table View for Part name or ID
     * @param searchEvent Created an Action Event for the search filtering to add to search button
     * @throws IOException Throws IOException if method doesn't work
     */
    @FXML
    public void partSearchBoxSelected(ActionEvent searchEvent) throws IOException {
        try {
            String input = partTextBox.getText();

            ObservableList<Part> parts = Inventory.lookupPart(input);

            if (parts.size() == 0) {
                int partID = Integer.parseInt(input);
                Part part = Inventory.lookupPart(partID);
                if (part != null) {
                    parts.add(part);
                }
            }
            partsTableView.setItems(parts);
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: We can not find a part that matches your query.");
            alert.showAndWait();
            return;
        }
    }

    /***
     * Deletes a part from all part list. Also displays a confirmation window prior to deleting item. User must
     * select OK to move forward.
     * @param deleteEvent Event being called to delete part. Also triggers confirmation window.
     */
    @FXML
    public void deletePartButtonSelected(ActionEvent deleteEvent) {
        if (partsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To delete a part, please first select a part you'd like to delete from the Parts table.");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /***
     * Deletes a product from all product list. Also triggers a confirmation warning prior to deleting item from list.
     * User must select OK to move forward.
     * @param deleteEvent Event being called to delete product. Also triggers confirmation window.
     */
    @FXML
    public void deleteProductButtonSelected(ActionEvent deleteEvent) {
        if (productsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To delete a product, please first select a product you'd like to delete from the Products table.");
            alert.show();
            return;
        }
        if (productsTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setContentText("Error: Please do not delete a Product that has Parts associated with it.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /***
     * Switches the main scene to the Add Part form scene.
     * @param event Event created to switch scenes.
     * @throws IOException Throws IOException if there is an error.
     */
    public void addPartButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/view/addPartForm.fxml"));
        Scene addPartScene = new Scene(addPartParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /***
     * Switches scenes to the Add Product Form scene when the add button is pressed.
     * @param addEvent Event created to switch scenes.
     * @throws IOException Throws exception if there is an error.
     */
    public void addProductButtonPushed(ActionEvent addEvent) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/view/addProductForm.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node) addEvent.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }

    /***
     * Switches scenes to the Modify Part Form scene. Also pre-populates the scene with selected part's data.
     * @param modifyEvent Event called to switch scenes.
     * @throws IOException Throws exception if there is an error.
     */
    public void modifyPartButtonPushed(ActionEvent modifyEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyPartForm.fxml"));
            Parent modifyPartParent = loader.load();
            Scene modifyPartScene = new Scene(modifyPartParent);

            //method below will call the initPart method, which populates new scene with selected Object's data
            modifyPartFormController controller = loader.getController();
            controller.initPart(partsTableView.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) modifyEvent.getSource()).getScene().getWindow();

            window.setScene(modifyPartScene);
            window.show();
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To modify a part, please first select a part from the Parts table.");
            alert.showAndWait();
        }
    }

    /***
     * Switches scenes to the Modify Product scene. Also populates new scene with selected object's data.
     * @param modifyEvent Event created to switch scenes.
     * @throws IOException Throws IO exception if there is an error.
     */
    public void modifyProductButtonPushed(ActionEvent modifyEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyProductForm.fxml"));
            Parent modifyProductParent = loader.load();
            Scene modifyProductScene = new Scene(modifyProductParent);

            //Code below calls initProduct method, which populates new scene with selected item's data
            modifyProductFormController controller = loader.getController();
            controller.initProduct(productsTableView.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) modifyEvent.getSource()).getScene().getWindow();

            window.setScene(modifyProductScene);
            window.show();
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To modify a product, please first select a product from the Products table.");
            alert.showAndWait();
        }
    }

    /***
     * Initializes the main scene and sets the table items with parts and products
     */
    public void initialize() {

        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

