package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.util.Optional;

public class addPartFormController {

    @FXML
    private RadioButton addPartInHouseRadioButton;

    @FXML
    private RadioButton addPartOutsourcedRadioButton;

    @FXML
    private TextField addPartIDTextField;

    @FXML
    private TextField addPartPriceTextField;

    @FXML
    private TextField addPartNameTextField;

    @FXML
    private TextField addPartMaxTextField;

    @FXML
    private TextField addPartStockTextField;

    @FXML
    private TextField addPartMinTextField;

    @FXML
    private Button saveAddPartButton;

    @FXML
    private Button cancelAddPartButton;

    @FXML
    private Label toggleGroupLabel;

    @FXML
    private TextField addPartVariableTextField;

    /***
     * Saves the new part and adds it to the allPart's list. This also Throws an error message if min/max values are
     * incompatible. Also throws an error message if data values are invalid.
     * @param saveEvent Event created to initiate the save event.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    void onActionPartSave(ActionEvent saveEvent) throws IOException {
        try {
            int id = Inventory.getAllParts().size() + 1;
            String name = addPartNameTextField.getText();
            int stock = Integer.parseInt(addPartStockTextField.getText());
            double price = Double.parseDouble(addPartPriceTextField.getText());
            int max = Integer.parseInt(addPartMaxTextField.getText());
            int min = Integer.parseInt(addPartMinTextField.getText());

            //This part checks min and max values and throws error if necessary
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
                alert.setContentText("Error: Please enter a valid name.");
                alert.showAndWait();
                return;
            }

            if (addPartVariableTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: Please enter a valid machine ID or company name.");
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

            if (price <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Error: Please enter a price that is greater than zero.");
                alert.showAndWait();
                return;
            }

            if (addPartInHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(addPartVariableTextField.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }

            if (addPartOutsourcedRadioButton.isSelected()) {
                String companyName = addPartVariableTextField.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            Parent saveAddPart = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScreen = new Scene(saveAddPart);

            Stage window = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        }   catch (Exception e) { //This will throw an error message if there is an error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Error: The data you entered is invalid. Please review and try again.");
            alert.showAndWait();
        }
    }

    /***
     * Confirms that user wants to cancel and returns them to the home screen. Also displays confirmation message.
     * @param cancelEvent Event created to initiate the confirmation message and begin event.
     * @throws IOException Throws an exception if there is an error.
     */
    public void cancelAddPartButtonPushed(ActionEvent cancelEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Cancel? This will delete unsaved data.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent cancelAddPart = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScreen = new Scene(cancelAddPart);

            Stage window = (Stage) ((Node) cancelEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        }
    }

    /***
     * If the in-house radio button is selected, it sets the "Machine ID" label.
     * @param e Event created for label change.
     */
    public void inHouseRadioPushed(ActionEvent e) {
        if (addPartInHouseRadioButton.isSelected()) {
            toggleGroupLabel.setText("Machine ID");
        }
    }

    /***
     * If the Outsourced radio button is selected, it sets the "Company Name" label.
     * @param e Event created for label change.
     */
    public void outsourcedRadioPushed(ActionEvent e) {
        if (addPartOutsourcedRadioButton.isSelected()) {
            toggleGroupLabel.setText("Company Name");
        }
    }
}
