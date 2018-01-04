package com.transport.view.controllers;

import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.state.StateManager;
import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.extern.log4j.Log4j;

import javax.inject.Inject;

@Log4j
public class InitController {

    @Inject
    ParticleApplication app;
    @FXML
    private AnchorPane init;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton registerButton;

    @Inject
    private ViewManager viewManager;

    @Inject
    private StateManager stateManager;

    private boolean first = true;

    public void postInit() {
        if (first) {
            stateManager.setPersistenceMode(StateManager.PersistenceMode.USER);
            first = false;
        }
    }

    @FXML
    void openLogin(ActionEvent event) {
        viewManager.switchView("login");
    }

    @FXML
    void openRegister(ActionEvent event) {
        viewManager.switchView("register");
    }
}
