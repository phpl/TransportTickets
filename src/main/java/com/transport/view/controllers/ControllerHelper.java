package com.transport.view.controllers;

import javafx.scene.control.Alert;

public class ControllerHelper {
    public static void succesRecordAdd() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Transport Bilety");
        alert.setGraphic(null);
        alert.setHeaderText("Sukces!");
        alert.setContentText("Dodano rekord");
        alert.showAndWait();
    }

    public static void errorWhileRecordAdd() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Transport Bilety");
        alert.setGraphic(null);
        alert.setHeaderText("Błąd!");
        alert.setContentText("Rekord nie mógł być dodany");
        alert.showAndWait();
    }
}
