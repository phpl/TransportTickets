package com.transport.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.controllers.SecondaryController;

@ParticleView(name = "secondary", isDefault = false)
public class SecondaryView extends FXMLView {
    
    public SecondaryView() {
        super(SecondaryView.class.getResource("secondary.fxml"));
    }
    
    @Override
    public void start() {
        ((SecondaryController) getController()).postInit();
    }
    
    @Override
    public void stop() {
        ((SecondaryController) getController()).dispose();
    }
    
}