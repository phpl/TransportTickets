package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.VehiclesFormController;

@ParticleView(name = "vehiclesForm", isDefault = false)
public class VehicleFormView extends FXMLView {

    public VehicleFormView() {
        super(VehicleFormView.class.getResource("vehiclesForm.fxml"));
    }

    @Override
    public void start() {
        ((VehiclesFormController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((VehiclesFormController) getController()).dispose();
    }
}
