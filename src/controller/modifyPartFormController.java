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
import model.Part;
import java.io.IOException;
import java.util.Optional;

public class modifyPartFormController {

    private Part modifiedPart;

    @FXML
    private RadioButton modifyPartInHouseRadioButton;

    @FXML
    private RadioButton modifyPartOutsourcedRadioButton;

    @FXML
    private TextField modifyPartIDTextField;

    @FXML
    private TextField modifyPartNameTextField;

    @FXML
    private TextField modifyPartPriceTextField;

    @FXML
    private TextField modifyPartMaxTextField;

    @FXML
    private TextField modifyPartMinTextField;

    @FXML
    private TextField modifyPartStockTextField;

    @FXML
    private Button saveModifyPartButton;

    @FXML
    private Button cancelModifyPartButton;

    @FXML
    private Label modifyToggleLabel;

    @FXML
    private TextField modifyPartVariableTextField;

    /***
     * Saves the product that has been modified and displays an error message if there were any errors in the data.
     * @param saveEvent Event created to initialize the save when button is clicked.
     * @throws IOException Throws an exception if there is an error.
     */
    @FXML
    private void modifySaveSelected(ActionEvent saveEvent) throws IOException {
        try {
            int id = Integer.parseInt(modifyPartIDTextField.getText());
            String name = modifyPartNameTextField.getText();
            int stock = Integer.parseInt(modifyPartStockTextField.getText());
            double price = Double.parseDouble(modifyPartPriceTextField.getText());
            int max = Integer.parseInt(modifyPartMaxTextField.getText());
            int min = Integer.parseInt(modifyPartMinTextField.getText());

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

            if (modifyPartInHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(modifyPartVariableTextField.getText());
                Inventory.updatePart(id - 1, new InHouse(id, name, price, stock, min, max, machineId));
            }

            if (modifyPartOutsourcedRadioButton.isSelected()) {
                String companyName = modifyPartVariableTextField.getText();
                Inventory.updatePart(id - 1, new Outsourced(id, name, price, stock, min, max, companyName));
            }

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
     * This helps the data in the associated tables get initialized when selected from another screen.
     * @param part The part who's associated parts will be initialized when scene is opened.
     */
    public void initPart(Part part) {
        modifiedPart = part;
        modifyPartIDTextField.setText(String.valueOf(part.getId()));
        modifyPartNameTextField.setText(part.getName());
        modifyPartStockTextField.setText(String.valueOf(part.getStock()));
        modifyPartPriceTextField.setText(String.valueOf(part.getPrice()));
        modifyPartMinTextField.setText(String.valueOf(part.getMin()));
        modifyPartMaxTextField.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            part = (InHouse) part;
            modifyPartVariableTextField.setText(String.valueOf(((InHouse) part).getMachineId()));
            modifyPartInHouseRadioButton.setSelected(true);
            modifyToggleLabel.setText("Machine ID");
        }

        if (part instanceof Outsourced) {
            part = (Outsourced) part;
            modifyPartVariableTextField.setText(((Outsourced) part).getCompanyName());
            modifyPartOutsourcedRadioButton.setSelected(true);
            modifyToggleLabel.setText("Company Name");
        }
    }

    /***
     * Cancels everything that has been entered. Displays a confirmation and will return to main screen
     * if OK button is pushed.
     * @param cancelEvent Event created to trigger cancel when cancel button is pushed.
     * @throws IOException Throws an exception if there is an error.
     */
    public void cancelModifyPartButtonPushed(ActionEvent cancelEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to return? Any unsaved data will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent cancelModifyPart = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
            Scene mainScreen = new Scene(cancelModifyPart);

            Stage window = (Stage) ((Node) cancelEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        }
    }

    /***
     * Sets the "Machine ID" label if in-house radio button is selected
     * @param e Event created when radio button is selected.
     */
    public void inHouseRadioSelected(ActionEvent e) {
        if (modifyPartInHouseRadioButton.isSelected()) {
            modifyToggleLabel.setText("Machine ID");
        }
    }

    /***
     * Sets the "Company Name" label if Outsourced radio button is selected.
     * @param e Event created when radio button is selected.
     */
    public void outsourcedRadioSelected(ActionEvent e) {
        if (modifyPartOutsourcedRadioButton.isSelected()) {
            modifyToggleLabel.setText("Company Name");
            modifyPartVariableTextField.clear();
        }
    }
}
