package com.transport.view.views;


import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.VehiclesController;

@ParticleView(name = "vehicles", isDefault = false)
public class VehiclesView extends FXMLView {
    public VehiclesView() {
        super(VehiclesView.class.getResource("vehicles.fxml"));
    }

    @Override
    public void start() {
        ((VehiclesController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((VehiclesController) getController()).dispose();
    }
}
