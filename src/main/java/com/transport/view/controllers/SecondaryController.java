package com.transport.view.controllers;

import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.extern.log4j.Log4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;

import javax.inject.Inject;
import java.util.ResourceBundle;

@Log4j
public class SecondaryController {

    @Inject
    ParticleApplication app;

    @Inject
    private ViewManager viewManager;

    @FXML
    private Button button;

    @FXML
    private ResourceBundle resources;

    private Action actionHome;

    public void initialize() {
        ActionMap.register(this);
        actionHome = ActionMap.action("goHome");

        button.setText(resources.getString("button.text"));
        button.setOnAction(e -> viewManager.switchView("primary"));
    }

    public void postInit() {
        app.getParticle().getToolBarActions().add(0, actionHome);
    }

    public void dispose() {
        app.getParticle().getToolBarActions().remove(actionHome);
    }

    @ActionProxy(text = "Back")
    private void goHome() {
        viewManager.switchView("primary");
    }

}
