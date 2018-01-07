package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.PassengerFormUserController;

@ParticleView(name = "passengerFormUser", isDefault = false)
public class PassengerFormUserView extends FXMLView {
    public PassengerFormUserView() {
        super(PassengerFormUserView.class.getResource("passengersFormUser.fxml"));
    }

    @Override
    public void start() {
        ((PassengerFormUserController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((PassengerFormUserController) getController()).dispose();
    }
}
