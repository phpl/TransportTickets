package com.transport.view.views;


import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.RegisterFormController;

@ParticleView(name = "registerForm", isDefault = false)
public class RegisterFormView extends FXMLView {
    public RegisterFormView() {
        super(RegisterFormView.class.getResource("registerForm.fxml"));
    }

    @Override
    public void start() {
        ((RegisterFormController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((RegisterFormController) getController()).dispose();
    }
}
