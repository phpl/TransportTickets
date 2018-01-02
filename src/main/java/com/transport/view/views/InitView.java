package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.InitController;

@ParticleView(name = "init", isDefault = true)
public class InitView extends FXMLView {

    public InitView() {
        super(InitView.class.getResource("init.fxml"));
    }

    @Override
    public void start() {
        ((InitController) getController()).postInit();
    }
}
