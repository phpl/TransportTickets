package com.transport;

import com.gluonhq.particle.application.ParticleApplication;
import javafx.scene.Scene;
import static org.controlsfx.control.action.ActionMap.actions;

public class TransportTickets extends ParticleApplication {

    public TransportTickets() {
        super("Transport");
    }

    @Override
    public void postInit(Scene scene) {
        scene.getStylesheets().add(TransportTickets.class.getResource("style.css").toExternalForm());

        setTitle("Transport");

        getParticle().buildMenu("File -> [exit]", "Help -> [about]");
        
        getParticle().getToolBarActions().addAll(actions("---", "about", "exit"));
    }
}