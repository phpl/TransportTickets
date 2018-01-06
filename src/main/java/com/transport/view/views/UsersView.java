package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.UsersController;

@ParticleView(name = "users", isDefault = false)
public class UsersView extends FXMLView {
    public UsersView() {
        super(UsersView.class.getResource("users.fxml"));
    }

    @Override
    public void start() {
        ((UsersController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((UsersController) getController()).dispose();
    }
}
