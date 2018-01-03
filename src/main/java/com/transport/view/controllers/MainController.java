package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.inject.Inject;

public class MainController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button driversButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button passengersButton;

    @FXML
    private Button logoutButton;

    @FXML
    void logout(ActionEvent event) {
        viewManager.switchView("login");
    }

    @FXML
    void openDrivers(ActionEvent event) {

    }

    @FXML
    void openPassengers(ActionEvent event) {

    }

    @FXML
    void openSchedule(ActionEvent event) {
        viewManager.switchView("schedule");
    }

    @FXML
    void openUsers(ActionEvent event) {

    }

}
