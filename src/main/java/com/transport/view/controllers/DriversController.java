package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public class DriversController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton usersButton1;

    @FXML
    private JFXButton passengersButton1;

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

    }

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void openPassengers(ActionEvent event) {

    }

    @FXML
    void openSchedules(ActionEvent event) {

    }

    @FXML
    void openUsers(ActionEvent event) {

    }

    @FXML
    void openVehicles(ActionEvent event) {

    }

}
