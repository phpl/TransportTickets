package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.ScheduleController;

@ParticleView(name = "schedule", isDefault = false)
public class ScheduleView extends FXMLView {
    public ScheduleView() {
        super(ScheduleView.class.getResource("schedule.fxml"));
    }

    @Override
    public void start() {
        ((ScheduleController) getController()).postInit();
    }
}