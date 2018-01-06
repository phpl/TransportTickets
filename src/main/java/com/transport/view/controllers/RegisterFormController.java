package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.transport.logic.RegisterFormLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class RegisterFormController {
    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField userInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXTextField firstNameInput;

    @FXML
    private JFXTextField lastNameInput;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXTextField cityInput;

    @FXML
    private JFXTextField streetInput;

    @FXML
    private JFXTextField houseNumberInput;

    @FXML
    private JFXButton registerButton;

    @FXML
    private JFXButton backButton;

    RegisterFormLogic logic;

    public void postInit() {
        logic = new RegisterFormLogic();
    }

    public void dispose() {
        logic.dispose();
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("init");
    }

    @FXML
    void registerAUser(ActionEvent event) {
        String userName = userInput.getText();
        String password = passwordInput.getText();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String houseNumber = houseNumberInput.getText();
        int phone = Integer.parseInt(phoneNumber.getCharacters().toString());

        logic.addUser(userName, password, firstName, lastName, phone, city, street, houseNumber);
    }

}

