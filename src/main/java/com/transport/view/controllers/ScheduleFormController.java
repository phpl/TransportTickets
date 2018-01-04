package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.transport.DatabaseService;
import com.transport.dao.*;
import com.transport.entity.RouteEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.sql.SQLException;

public class ScheduleFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private TextField beginCityInput;

    @FXML
    private TextField endCityInput;

    @FXML
    private TextField distanceInput;

    @FXML
    private TextField departureInput;

    @FXML
    private TextField arrivalInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField licencePlateInput;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Label infoLabel;

    private DatabaseService databaseService;
    private CourseDao courseDao;
    private CourseDriverDao courseDriverDao;
    private CourseVehicleDao courseVehicleDao;
    private TicketDao ticketDao;
    private RouteDao routeDao;

    public void postInit() {
        databaseService = new DatabaseService();
        courseDao = new CourseDao(databaseService);
        courseDriverDao = new CourseDriverDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        routeDao = new RouteDao(databaseService);
        databaseService.connectToDatabase();
    }

    @FXML
    void addCourse(ActionEvent event) {
        RouteEntity routeEntity = getRouteEntityFromForm();

        databaseService.setAutoCommit(false);
        try {
            routeDao.insertRoute(routeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);

        ControllerHelper.succesRecordAdd();

//        CourseEntity courseEntity = new CourseEntity(departureInput.getCharacters(), arrivalInput.getCharacters(), 2, );
//        courseDao.insertCourse();
        //kurs, trasa, kierowca_trasa, pojazd
    }

    private RouteEntity getRouteEntityFromForm() {
        int distance = Integer.parseInt(distanceInput.getCharacters().toString());
        String beginCity = beginCityInput.getText();
        String endCity = endCityInput.getText();

        return new RouteEntity(distance, beginCity, endCity);
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("schedule");
    }

}

