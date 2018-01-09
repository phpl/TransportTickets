package com.transport.view.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.view.FXMLView;
import com.transport.view.controllers.ScheduleUpdateFormController;

@ParticleView(name = "scheduleUpdateForm", isDefault = false)
public class ScheduleUpdateFormView extends FXMLView {
    public ScheduleUpdateFormView() {
        super(ScheduleUpdateFormView.class.getResource("scheduleUpdateForm.fxml"));
    }

    @Override
    public void start() {
        ((ScheduleUpdateFormController) getController()).postInit();
    }

    @Override
    public void stop() {
        ((ScheduleUpdateFormController) getController()).dispose();
    }
}
