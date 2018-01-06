package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.ScheduleFormController;

@ParticleView(name = "scheduleForm", isDefault = false)
public class ScheduleFormView extends FXMLView {
    public ScheduleFormView() {
        super(ScheduleFormView.class.getResource("scheduleForm.fxml"));
    }

    @Override
    public void start() {
        ((ScheduleFormController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((ScheduleFormController) getController()).dispose()
    }
}
