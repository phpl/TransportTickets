package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.MainController;

@ParticleView(name = "main", isDefault = false)
public class MainView extends FXMLView {
    public MainView() {
        super(MainView.class.getResource("main.fxml"));
    }

    @Override
    public void start() {
        ((MainController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((MainController) getController()).dispose();
    }
}