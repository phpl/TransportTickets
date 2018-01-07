package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.transport.Account;
import com.transport.logicForm.PassengersFormLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class PassengerFormUserController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField luggageWeightInput;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    private PassengersFormLogic logic;

    public void postInit() {
        logic = new PassengersFormLogic();
    }

    public void dispose() {
        logic.dispose();
    }

    @FXML
    void add(ActionEvent event) {
        String luggageWeightString = luggageWeightInput.getCharacters().toString();
        Double luggageWeight = luggageWeightString.isEmpty() ? null :
                Double.parseDouble(luggageWeightString) == 0 ? null : Double.parseDouble(luggageWeightString);

        if (ScheduleController.selectedCourseId != null && Account.currentUserId != null) {
            logic.addPassenger(
                    Account.currentUserId,
                    ScheduleController.selectedCourseId,
                    ScheduleController.selectedCoursePrice,
                    luggageWeight);
            ScheduleController.selectedCourseId = null;
            ScheduleController.selectedCoursePrice = null;
        }
        viewManager.switchView("schedule");
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("schedule");
    }

}
