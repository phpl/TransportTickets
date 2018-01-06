package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.PassengersController;

@ParticleView(name = "passengers", isDefault = false)
public class PassengersView extends FXMLView {
    public PassengersView() {
        super(PassengersView.class.getResource("passengers.fxml"));
    }

    @Override
    public void start() {
        ((PassengersController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((PassengersController) getController()).dispose();
    }
}
