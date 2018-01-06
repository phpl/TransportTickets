package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.transport.logic.ScheduleFormLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.time.LocalTime;

public class ScheduleFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField beginCityInput;

    @FXML
    private JFXTextField endCityInput;

    @FXML
    private JFXTextField distanceInput;

    @FXML
    private JFXTimePicker departureInput;

    @FXML
    private JFXTimePicker arrivalInput;

    @FXML
    private JFXTextField phoneNumberInput;

    @FXML
    private JFXTextField licencePlateInput;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private Label infoLabel;

    private ScheduleFormLogic logic;

    public void postInit() {
        logic = new ScheduleFormLogic();
        departureInput.setIs24HourView(true);
        arrivalInput.setIs24HourView(true);
    }

    public void dispose() {
        logic.dispose();
    }

    @FXML
    void addCourse(ActionEvent event) {
        String beginCity = beginCityInput.getText();
        String endCity = endCityInput.getText();
        int distance = Integer.parseInt(distanceInput.getCharacters().toString());
        LocalTime departureTime = departureInput.getValue();
        LocalTime arrivalTime = arrivalInput.getValue();
        int phoneNumber = Integer.parseInt(phoneNumberInput.getCharacters().toString());
        String licencePlate = licencePlateInput.getCharacters().toString();

        logic.insertCourseView(beginCity, endCity, distance, departureTime, arrivalTime, phoneNumber, licencePlate);

        viewManager.switchView("schedule");
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("schedule");
    }

}

