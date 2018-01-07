package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class MainController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton driversButton;

    @FXML
    private JFXButton usersButton;

    @FXML
    private JFXButton passengersButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton vehiclesButton;

    public void postInit() {
        switch (Account.type) {
            case USER:
                driversButton.setText("");
                usersButton.setText("");
                vehiclesButton.setText("");
                break;
            case GUEST:
                driversButton.setText("");
                usersButton.setText("");
                passengersButton.setText("");
                vehiclesButton.setText("");
                break;
        }
    }

    public void dispose() {
        driversButton.setText("Kierowcy");
        usersButton.setText("Użytkownicy");
        passengersButton.setText("Pasażerowie");
        vehiclesButton.setText("Pojazdy");
    }

    @FXML
    void openVehicles(ActionEvent event) {
        if (Account.type == Account.AccountType.ADMINISTRATOR) {
            viewManager.switchView("vehicles");
        }
    }

    @FXML
    void logout(ActionEvent event) {
        viewManager.switchView("login");
    }

    @FXML
    void openDrivers(ActionEvent event) {
        if (Account.type == Account.AccountType.ADMINISTRATOR) {
            viewManager.switchView("drivers");
        }
    }

    @FXML
    void openPassengers(ActionEvent event) {
        if (Account.type != Account.AccountType.GUEST) {
            viewManager.switchView("passengers");
        }
    }

    @FXML
    void openSchedule(ActionEvent event) {
        viewManager.switchView("schedule");
    }

    @FXML
    void openUsers(ActionEvent event) {
        if (Account.type == Account.AccountType.ADMINISTRATOR) {
            viewManager.switchView("users");
        }
    }

}
