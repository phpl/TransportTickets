package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.transport.logicForm.ScheduleFormUpdateLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class ScheduleUpdateFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField driverPhoneNumberInput;

    @FXML
    private JFXTextField licencePlateInput;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    private ScheduleFormUpdateLogic logic;

    public void postInit() {
        logic = new ScheduleFormUpdateLogic();

    }

    public void dispose() {
        logic.dispose();
    }

    @FXML
    void add(ActionEvent event) {
        int phoneNumber = Integer.parseInt(driverPhoneNumberInput.getCharacters().toString());
        String licencePlate = licencePlateInput.getCharacters().toString();

        logic.updateCourse(ScheduleController.selectedCourseId, phoneNumber, licencePlate);
        viewManager.switchView("schedule");
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("schedule");
    }
}
