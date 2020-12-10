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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.util.Optional;

public class modifyProductFormController {

    private Product modifiedProduct;

    @FXML
    private ObservableList<Part> associatedList = FXCollections.observableArrayList();

    @FXML
    private TextField modifyProductIDTextField;

    @FXML
    private TextField modifyProductNameTextField;

    @FXML
    private TextField modifyProductStockTextField;

    @FXML
    private TextField modifyProductPriceTextField;

    @FXML
    private TextField modifyProductMaxTextField;

    @FXML
    private TextField modifyProductMinTextField;

    @FXML
    private Button saveModifyProductButton;

    @FXML
    private Button cancelModifyProductButton;

    @FXML
    private TableView<Part> modifyProductAllPartsTableView;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partStockColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private Button addPartToAssociatedPartButton;

    @FXML
    private TableView<Part> associatedPartsTableView;

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
    private Button modifyProductSearchPartButton;

    @FXML
    private TextField modifyProductSearchPartTextField;

    /***
     * Filters the parts table for the part name or ID when entered in text field.
     * @param event Event created to initialize the filter when search button is clicked.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    public void onActionModifyPartSearchBoxSelected(ActionEvent event) throws IOException {
        try {
            String input = modifyProductSearchPartTextField.getText();
            ObservableList<Part> parts = Inventory.lookupPart(input);

            if (parts.size() == 0) {
                int partID = Integer.parseInt(input);
                Part part = Inventory.lookupPart(partID);
                if (part != null) {
                    parts.add(part);
                }
            }
            modifyProductAllPartsTableView.setItems(parts);
        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: We can not find a part that matches your query.");
            alert.showAndWait();
            return;
        }
    }

    /***
     * Saves the product that was modified and updates the allProducts list. Also displays errors if necessary.
     * Will take user back to main screen.
     * @param saveEvent Event created to initialize the save and switch screens when button is selected.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    public void modifyProductSaveSelected(ActionEvent saveEvent) throws IOException {
        try {
            int id = Integer.parseInt(modifyProductIDTextField.getText());
            String name = modifyProductNameTextField.getText();
            int stock = Integer.parseInt(modifyProductStockTextField.getText());
            double price = Double.parseDouble(modifyProductPriceTextField.getText());
            int max = Integer.parseInt(modifyProductMaxTextField.getText());
            int min = Integer.parseInt(modifyProductMinTextField.getText());

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

            for (int i = 0; i < associatedList.size(); i++) {
                product.addAssociatedPart(associatedList.get(i));
                count += associatedList.get(i).getPrice();
            }

            if (product.getPrice() < count) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: The price of the product must be greater than the total price of its parts.");
                alert.showAndWait();
                return;
            }

            Inventory.updateProduct(id - 1, product);

            Parent saveAddProduct = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScreen = new Scene(saveAddProduct);

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
     * Adds a part to the associated parts table for the product.
     * @param addEvent Event created to initialize the addition when button is selected.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    public void onActionAddPartButtonPushed(ActionEvent addEvent) throws IOException {
        if (modifyProductAllPartsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To add a part, please first select a part you'd like to add from the Parts table.");
            alert.show();
            return;
        }
        if (associatedList.contains(modifyProductAllPartsTableView.getSelectionModel().getSelectedItem())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: You can not add the same part twice.");
            alert.show();
            return;
        }
        associatedList.add(modifyProductAllPartsTableView.getSelectionModel().getSelectedItem());
    }

    /***
     * Removes a part from the associated product list for the product. Also displays confirmation message.
     * @param removeEvent Event created to initialize the removal when selected.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    public void onActionRemovePartButtonPushed(ActionEvent removeEvent) throws IOException {
        if (associatedPartsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("To remove a part, please first select a part you'd like to remove from the Associated Parts table.");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedList.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /***
     * Cancels the data that has been entered. Displays a confirmation and returns user to main screen
     * if OK is selected.
     * @param event Event created to initialize the cancel when button is selected.
     * @throws IOException Throws an exception if there is an error.
     */
    public void cancelModifyProductButtonPushed(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to return? Any unsaved data will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent cancelModifyProduct = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScreen = new Scene(cancelModifyProduct);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        }
    }

    /***
     * Initializes the associated parts table when the Product is selected from the main screen.
     * @param product Product to be modified who's data will populate the screen when method is called.
     */
    public void initProduct(Product product) {
        modifiedProduct = product;
        modifyProductIDTextField.setText(String.valueOf(product.getId()));
        modifyProductNameTextField.setText(product.getName());
        modifyProductStockTextField.setText(String.valueOf(product.getStock()));
        modifyProductPriceTextField.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTextField.setText(String.valueOf(product.getMax()));
        modifyProductMinTextField.setText(String.valueOf(product.getMin()));

        associatedList = product.getAllAssociatedParts();
        associatedPartsTableView.setItems(associatedList);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductAllPartsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
