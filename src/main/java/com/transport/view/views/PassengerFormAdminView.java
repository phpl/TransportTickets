package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.PassengerFormAdminController;

@ParticleView(name = "passengerFormAdmin", isDefault = false)
public class PassengerFormAdminView extends FXMLView {
    public PassengerFormAdminView() {
        super(PassengerFormAdminView.class.getResource("passengersFormAdmin.fxml"));
    }

    @Override
    public void start() {
        ((PassengerFormAdminController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((PassengerFormAdminController) getController()).dispose();
    }
}
