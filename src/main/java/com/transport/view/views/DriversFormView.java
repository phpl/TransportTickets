package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.DriversFormController;

@ParticleView(name = "driversForm", isDefault = false)
public class DriversFormView extends FXMLView {
    public DriversFormView() {
        super(DriversFormView.class.getResource("driversForm.fxml"));
    }

    @Override
    public void start() {
        ((DriversFormController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((DriversFormController) getController()).dispose();
    }
}
