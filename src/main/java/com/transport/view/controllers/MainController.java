package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
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
    void logout(ActionEvent event) {
        viewManager.switchView("login");
    }

    @FXML
    void openDrivers(ActionEvent event) {
        viewManager.switchView("drivers");
    }

    @FXML
    void openPassengers(ActionEvent event) {
        viewManager.switchView("passengers");
    }

    @FXML
    void openSchedule(ActionEvent event) {
        viewManager.switchView("schedule");
    }

    @FXML
    void openUsers(ActionEvent event) {
        viewManager.switchView("users");
    }

}
