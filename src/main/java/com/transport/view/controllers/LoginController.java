package com.transport.view.controllers;


import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.transport.Account;
import com.transport.DatabaseService;
import com.transport.dao.UserDao;
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
        Account.type = Account.AccountType.GUEST;
        viewManager.switchView("main");
    }

    private DatabaseService databaseService;
    private UserDao userDao;

    public void postInit() {
        Account.currentUserId = null;
        databaseService = new DatabaseService();
        userDao = new UserDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    @FXML
    void loginAsUser(ActionEvent event) {
        String username = loginInput.getText();
        String password = passwordInput.getText();

        boolean isValidCredentials = userDao.checkUserCredentials(username, password);
        boolean canLogin = checkIfCanLogin(isValidCredentials, username);

        if (canLogin) {
            Account.currentUserId = userDao.getUserId(username);
            viewManager.switchView("main");
        }
    }

    private boolean checkIfCanLogin(boolean isValidCredentials, String username) {
        boolean canLogin = false;

        if (isValidCredentials) {
            canLogin = true;
            Account.type = username.equals("admin") ?
                    Account.AccountType.ADMINISTRATOR : Account.AccountType.USER;
        }

        return canLogin;
    }


}
