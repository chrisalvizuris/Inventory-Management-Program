module Inventory.Management.System {
    requires javafx.controls;
    requires javafx.fxml;

    opens view;
    opens controller;
    opens main;
    opens model;
}