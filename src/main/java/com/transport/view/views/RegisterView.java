package com.transport.view.views;


import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.RegisterController;

@ParticleView(name = "register", isDefault = false)
public class RegisterView extends FXMLView {
    public RegisterView() {
        super(RegisterView.class.getResource("register.fxml"));
    }

    @Override
    public void start() {
        ((RegisterController) getController()).postInit();
    }
}
