package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class RegisterController {
    @Inject
    private ViewManager viewManager;
    @FXML
    private PasswordField passwordInput;

    @FXML
    private PasswordField repeatPasswordInput;

    @FXML
    private TextField userInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField streetInput;

    @FXML
    private TextField houseNumberInput;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private Label infoLabel;

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("init");
    }

    @FXML
    void registerAUser(ActionEvent event) {

    }

}

