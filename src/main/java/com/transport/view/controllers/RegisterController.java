package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

public class RegisterController {
    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField userInput;

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
    private JFXPasswordField passwordInput;

    @FXML
    private JFXPasswordField repeatPasswordInput;

    @FXML
    private JFXButton registerButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private Label infoLabel;

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("init");
    }

    @FXML
    void registerAUser(ActionEvent event) {
//TODO add uzytkownik, dane_osobowe_adres
    }

}

