package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.DriversController;

@ParticleView(name = "drivers", isDefault = false)
public class DriversView extends FXMLView {
    public DriversView() {
        super(DriversView.class.getResource("drivers.fxml"));
    }

    @Override
    public void start() {
        ((DriversController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((DriversController) getController()).dispose();
    }
}
