package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.transport.logicForm.DriversFormLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class DriversFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField firstNameInput;

    @FXML
    private JFXTextField lastNameInput;

    @FXML
    private JFXTextField phoneNumberInput;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    private DriversFormLogic logic;

    public void postInit() {
        logic = new DriversFormLogic();
    }

    public void dispose() {
        logic.dispose();
    }

    @FXML
    void addCourse(ActionEvent event) {
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String phoneNumber = phoneNumberInput.getText();
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("drivers");
    }

}
