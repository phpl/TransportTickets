package com.transport.view.controllers;


import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

public class LoginController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField loginInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private JFXButton loginGuestButton;

    @FXML
    private JFXButton backButton;

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("init");
    }

    @FXML
    void loginAsGuest(ActionEvent event) {
        viewManager.switchView("main");
    }

    @FXML
    void loginAsUser(ActionEvent event) {
        viewManager.switchView("main");
    }


}
