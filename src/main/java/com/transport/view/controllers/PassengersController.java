package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public class PassengersController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton driversButton;

    @FXML
    private JFXButton usersButton1;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton logoutButton1;

    @FXML
    private JFXButton vehiclesButton;

    @FXML
    private TableView<?> tableView;

    @FXML
    private JFXButton addButton;

    private DatabaseService databaseService;


    public void postInit() {
        databaseService = new DatabaseService();
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    @FXML
    void addRecord(ActionEvent event) {
        viewManager.switchView("passengersForm");
    }

    @FXML
    void openVehicles(ActionEvent event) {
        viewManager.switchView("vehicles");
    }

    @FXML
    void logout(ActionEvent event) {
        viewManager.switchView("login");
    }

    @FXML
    void openSchedules(ActionEvent event) {
        viewManager.switchView("schedule");
    }

    @FXML
    void openUsers(ActionEvent event) {
        viewManager.switchView("users");
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("main");
    }

    @FXML
    void openDrivers(ActionEvent event) {
        viewManager.switchView("drivers");
    }

}
