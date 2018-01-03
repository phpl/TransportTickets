package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.inject.Inject;

public class ScheduleController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private Button backButton;

    @FXML
    private Button driversButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button passengersButton;

    @FXML
    private Button logoutButton;

    @FXML
    void backButton(ActionEvent event) {
        viewManager.switchView("main");
    }

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
    void openUsers(ActionEvent event) {

    }

}
