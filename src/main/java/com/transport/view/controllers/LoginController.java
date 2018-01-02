package com.transport.view.controllers;


import com.gluonhq.particle.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class LoginController {

    @Inject
    private ViewManager viewManager;
    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField loginInput;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginGuestButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Button backButton;

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("init");
    }

    @FXML
    void loginAsGuest(ActionEvent event) {

    }

    @FXML
    void loginAsUser(ActionEvent event) {

    }


}
