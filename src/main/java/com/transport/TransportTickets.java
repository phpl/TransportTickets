package com.transport;

import com.gluonhq.particle.application.ParticleApplication;
import javafx.scene.Scene;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;

import static org.controlsfx.control.action.ActionMap.actions;

@Log4j
public class TransportTickets extends ParticleApplication {

    public TransportTickets() {
        super("Transport");
        BasicConfigurator.configure();
    }

    @Override
    public void postInit(Scene scene) {
        scene.getStylesheets().add(TransportTickets.class.getResource("style.css").toExternalForm());
        setTitle("Transport");
        getParticle().buildMenu("File -> [exit]", "Help -> [about]");
        getParticle().getToolBarActions().addAll(actions("---", "about", "exit"));
    }
}