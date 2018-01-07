package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.transport.Account;
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

    @FXML
    private JFXTextField courseId;

    private DatabaseService databaseService;


    public void postInit() {
        databaseService = new DatabaseService();
        databaseService.connectToDatabase();
        ControllerHelper.resetButtonTexts(driversButton, usersButton1, null, vehiclesButton);
        ControllerHelper.hideButtonDependindOnAccountType(driversButton, usersButton1, null, vehiclesButton);
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
        Account.type = null;
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
