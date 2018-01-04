package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.transport.DatabaseService;
import com.transport.dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class ScheduleFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private TextField beginCityInput;

    @FXML
    private PasswordField endCityInput;

    @FXML
    private PasswordField distanceInput;

    @FXML
    private TextField departureInput;

    @FXML
    private TextField arrivalInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField licencePlateInput;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Label infoLabel;

    private DatabaseService databaseService;
    private CourseDao courseDao;
    private CourseDriverDao courseDriverDao;
    private CourseVehicleDao courseVehicleDao;
    private TicketDao ticketDao;
    private LuggageDao luggageDao;

    public void postInit() {
        databaseService = new DatabaseService();
        courseDao = new CourseDao(databaseService);
        courseDriverDao = new CourseDriverDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        luggageDao = new LuggageDao(databaseService);
        databaseService.connectToDatabase();
    }

    @FXML
    void addCourse(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("schedule");
    }

}

