package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;

@ParticleView(name = "login", isDefault = false)
public class LoginView extends FXMLView {
    public LoginView() {
        super(LoginView.class.getResource("login.fxml"));
    }
}
