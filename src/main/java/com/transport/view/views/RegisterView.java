package com.transport.view.views;


import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;

@ParticleView(name = "register", isDefault = false)
public class RegisterView extends FXMLView {
    public RegisterView() {
        super(RegisterView.class.getResource("register.fxml"));
    }
}
