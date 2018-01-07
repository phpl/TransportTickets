package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.transport.logicForm.VehiclesFormLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.inject.Inject;

public class VehiclesFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTextField modelInput;

    @FXML
    private JFXTextField licencePlateInput;

    @FXML
    private JFXTextField seatsNumberInput;

    @FXML
    private JFXTextField luggageWeightInput;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

    private VehiclesFormLogic logic;

    public void postInit() {
        logic = new VehiclesFormLogic();
    }

    public void dispose() {
        logic.dispose();
    }

    @FXML
    void add(ActionEvent event) {
        String model = modelInput.getText();
        String licencePlate = licencePlateInput.getText();
        int seatsNumber = Integer.parseInt(seatsNumberInput.getCharacters().toString());
        double luggageWeight = Double.parseDouble(luggageWeightInput.getCharacters().toString());

        logic.addVehicle(model, licencePlate, seatsNumber, luggageWeight);
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("vehicles");
    }
}
