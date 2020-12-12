package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;

    /***
     * Rubric G:
     * A compatible feature that should be added when I work on the next version of this program will be adding a database so that I can allow this program to save work once the program is shut off. A logical error that I ran into was with my search box (all of them). I couldn't get it to filter for letters. My issue was that I wasn't using the lookupPart method and including the user input as a parameter. Once I added the parameter, my search box worked. An example of this search box is in the mainFormController, specifically for the partSearchBoxSelected ActionEvent.
     */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainform.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    /***
     * Sets some dummy data when program is launched. Launches application.
     * @param args Item to be launched.
     */
    public static void main(String[] args) {

        InHouse part1 = new InHouse(1, "Brakes", 200.00, 10, 1,100,01);
        InHouse part2 = new InHouse(2, "Wheel", 125.00, 25,1,100,02);

        Inventory.addPart(part1);
        Inventory.addPart(part2);

        Product product1 = new Product(1,"Trek Bike",2000.00,25,1,100);
        Product product2 = new Product(2,"Giant Tricycle",1000.00,10,1,100);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }
}
