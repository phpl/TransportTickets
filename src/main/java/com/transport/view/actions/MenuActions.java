package com.transport.view.actions;

import com.gluonhq.particle.annotation.ParticleActions;
import com.gluonhq.particle.application.ParticleApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.extern.log4j.Log4j;
import org.controlsfx.control.action.ActionProxy;

import javax.inject.Inject;

@ParticleActions
@Log4j
public class MenuActions {

    @Inject
    ParticleApplication app;

    @ActionProxy(text = "Wyjscie", accelerator = "alt+F4")
    private void exit() {
        app.exit();
    }

    @ActionProxy(text = "O programie")
    private void about() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Transport Bilety");
        alert.setHeaderText("Projekt bazodanowy 1");
        alert.setContentText("Stworzony przez: Paweł Hadam");
        alert.showAndWait();
    }

}