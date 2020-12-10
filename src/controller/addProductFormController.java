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
import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class addProductFormController {

    @FXML
    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    @FXML
    private TextField addProductIDTextField;

    @FXML
    private TextField addProductNameTextField;

    @FXML
    private TextField addProductStockTextField;

    @FXML
    private TextField addProductPriceTextField;

    @FXML
    private TextField addProductMaxTextField;

    @FXML
    private TextField addProductMinTextField;

    @FXML
    private Button saveAddProductButton;

    @FXML
    private Button cancelAddProductButton;

    @FXML
    private TableView<Part> addPartsToProductTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIDColumn;

    @FXML
    private TableColumn<Part, String> addPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> addPartStockColumn;

    @FXML
    private TableColumn<Part, Double> addPartPriceColumn;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDColumn;

    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> associatedPartStockColumn;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button addPartToAssociatedPartTableViewButton;

    @FXML
    private Button addProductSearchPartButton;

    @FXML
    private TextField partSearchTextField;

    /***
     * Filters part table to part if part name or ID have been entered.
     * @param event Event created to trigger table filtering.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    public void onActionPartSearchButtonSelected(ActionEvent event) throws IOException {
        try {
            String input = partSearchTextField.getText();
            ObservableList<Part> parts = Inventory.lookupPart(input);

            if (parts.size() == 0) {
                int partID = Integer.parseInt(input);
                Part part = Inventory.lookupPart(partID);
                if (part != null) {
                    parts.add(part);
                }
            }
            addPartsToProductTableView.setItems(parts);
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: We can not find a part that matches your query.");
            alert.showAndWait();
            return;
        }
    }

    /***
     * Removes the selected part from the associated parts table. Also displays confirmation message.
     * @param removeEvent Event created to trigger the removal.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    void onActionRemoveAssociatedPartButtonClicked(ActionEvent removeEvent) throws IOException {
        if (associatedPartTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To remove a part, please first select a part you'd like to remove from the Associated Parts table.");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedPartList.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
        }
    }

    /***
     * Adds the selected part to the associated parts table for the product.
     * @param addEvent Event created to initiate the addition.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    void onActionAddToAssociatedPartClicked(ActionEvent addEvent) throws IOException {
        if (addPartsToProductTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To add a part, please first select a part you'd like to add from the Parts table.");
            alert.show();
            return;
        }

        if (associatedPartList.contains(addPartsToProductTableView.getSelectionModel().getSelectedItem())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: You can not add the same part twice.");
            alert.show();
            return;
        }
        associatedPartList.add(addPartsToProductTableView.getSelectionModel().getSelectedItem());
    }

    /***
     * Saves the product to the allProduct list. Also checks for errors and displays messages when necessary.
     * Takes the user back to the main screen.
     * @param saveEvent Event created to initiate the save.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    void onActionProductSave(ActionEvent saveEvent) throws IOException {
        try {
            int id = Inventory.getAllProducts().size() + 1;
            String name = addProductNameTextField.getText();
            int stock = Integer.parseInt(addProductStockTextField.getText());
            double price = Double.parseDouble(addProductPriceTextField.getText());
            int max = Integer.parseInt(addProductMaxTextField.getText());
            int min = Integer.parseInt(addProductMinTextField.getText());

            if ((stock < min) || (stock > max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: Please enter a Stock value between the Min and Max numbers.");
                alert.showAndWait();
                return;
            }

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: The data you entered is invalid. Please review and try again.");
                alert.showAndWait();
                return;
            }

            if (price <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: Please enter a price greater than zero.");
                alert.showAndWait();
                return;
            }

            if (min <= 0 || min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: Please enter a valid minimum value.");
                alert.showAndWait();
                return;
            }

            Product product = new Product(id, name, price, stock, min, max);

            int count = 0;

            for (int i = 0; i < associatedPartList.size(); i++) {
                product.addAssociatedPart(associatedPartList.get(i));
                count += associatedPartList.get(i).getPrice();
            }

            if (product.getPrice() < count) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: The price of the product must be greater than the total price of its associated parts.");
                alert.showAndWait();
                return;
            }

            Inventory.addProduct(product);

            Parent saveAddPart = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScreen = new Scene(saveAddPart);

            Stage window = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: The data you entered is invalid. Please review and try again.");
            alert.showAndWait();
        }
    }

    /***
     * Cancels the actions on the page and takes user back to main screen. Also displays a confirmation message.
     * @param cancelEvent Event created to initiate the cancel if button is pressed.
     * @throws IOException Throws an exception if there is an error.
     */
    public void cancelAddProductButtonPushed(ActionEvent cancelEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to return? Any unsaved data will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent cancelAddProduct = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScene = new Scene(cancelAddProduct);

            Stage window = (Stage) ((Node) cancelEvent.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.show();
        }
    }

    /***
     * Initializes the screen and sets the tables with items.
     */
    public void initialize() {
        addPartsToProductTableView.setItems(Inventory.getAllParts());
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.setItems(associatedPartList);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
