package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;

@ParticleView(name = "main", isDefault = false)
public class MainView extends FXMLView {
    public MainView() {
        super(MainView.class.getResource("main.fxml"));
    }
}