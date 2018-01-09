package com.transport.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.transport.Account;
import com.transport.exceptions.DatabaseException;
import javafx.scene.control.Alert;

public class ControllerHelper {
    public static void errorWhileRecordAdd() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Transport Bilety");
        alert.setGraphic(null);
        alert.setHeaderText("Błąd!");
        alert.setContentText("Rekord nie mógł być dodany");
        alert.showAndWait();
    }

    public static void validateIds(int id) throws DatabaseException {
        if (id == -1) {
            throw new DatabaseException();
        }
    }

    public static void hideButtonDependindOnAccountType(JFXButton driversButton,
                                                        JFXButton usersButton,
                                                        JFXButton passengersButton,
                                                        JFXButton vehiclesButton) {
        if (Account.type == null) {
            return;
        }

        switch (Account.type) {
            case USER:
                setDriverButton(driversButton, "");
                setUserButton(usersButton, "");
                setVehiclesButton(vehiclesButton, "");
                break;
            case GUEST:
                setDriverButton(driversButton, "");
                setUserButton(usersButton, "");
                setVehiclesButton(vehiclesButton, "");
                setPassengersButton(passengersButton, "");
                break;
            default:
                break;
        }
    }

    public static void resetButtonTexts(JFXButton driversButton,
                                        JFXButton usersButton,
                                        JFXButton passengersButton,
                                        JFXButton vehiclesButton) {
        setDriverButton(driversButton, "Kierowcy");
        setUserButton(usersButton, "Użytkownicy");
        setPassengersButton(passengersButton, "Pasażerowie");
        setVehiclesButton(vehiclesButton, "Pojazdy");
    }

    private static void setDriverButton(JFXButton driversButton, String text) {
        if (driversButton != null) {
            driversButton.setText(text);
        }
    }

    private static void setUserButton(JFXButton usersButton, String text) {
        if (usersButton != null) {
            usersButton.setText(text);
        }
    }

    private static void setPassengersButton(JFXButton passengersButton, String text) {
        if (passengersButton != null) {
            passengersButton.setText(text);
        }
    }

    private static void setVehiclesButton(JFXButton vehiclesButton, String text) {
        if (vehiclesButton != null) {
            vehiclesButton.setText(text);
        }
    }
}
