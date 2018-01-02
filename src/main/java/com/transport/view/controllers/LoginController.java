package com.transport.view.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {


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

    public void postInit() {
    }

    public void dispose() {

    }

    @FXML
    void loginAsGuest(ActionEvent event) {

    }

    @FXML
    void loginAsUser(ActionEvent event) {

    }


}
