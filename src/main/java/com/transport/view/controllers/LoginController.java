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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginController {

    private Properties properties = null;

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
        loadProperties();
        Account.currentUserId = null;
        databaseService = new DatabaseService();
        userDao = new UserDao(databaseService);
    }

    public void dispose() {
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
            Account.type = username.equals(properties.getProperty("ADMIN_LOGIN")) ?
                    Account.AccountType.ADMINISTRATOR : Account.AccountType.USER;
        }

        return canLogin;
    }

    private void loadProperties() {
        properties = new Properties();
        InputStream stream = null;

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            stream = classLoader.getResourceAsStream("application.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
