package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.transport.DatabaseService;
import com.transport.dao.*;
import com.transport.entity.CourseEntity;
import com.transport.entity.RouteEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.sql.SQLException;

public class ScheduleFormController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXTimePicker departureInput;

    @FXML
    private JFXTimePicker arrivalInput;

    @FXML
    private JFXTextField beginCityInput;

    @FXML
    private JFXTextField endCityInput;

    @FXML
    private JFXTextField distanceInput;

    @FXML
    private JFXTextField phoneNumberInput;

    @FXML
    private JFXTextField licencePlateInput;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton backButton;

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
        departureInput.setIs24HourView(true);
        arrivalInput.setIs24HourView(true);
    }

    @FXML
    void addCourse(ActionEvent event) {
        RouteEntity routeEntity = createRouteEntity();

        databaseService.setAutoCommit(false);
        try {
            routeDao.insertRoute(routeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);


        CourseEntity courseEntity = createCourseEntity(routeEntity);
        databaseService.setAutoCommit(false);
        try {
            courseDao.insertCourse(courseEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);

        ControllerHelper.succesRecordAdd();
//TODO add kierowca_trasa, kierowca_pojazd
    }

    private RouteEntity createRouteEntity() {
        int distance = Integer.parseInt(distanceInput.getCharacters().toString());
        String beginCity = beginCityInput.getText();
        String endCity = endCityInput.getText();

        return new RouteEntity(distance, beginCity, endCity);
    }

    private CourseEntity createCourseEntity(RouteEntity routeEntity) {

        int routeId = routeDao.getRouteIdOfItem(routeEntity);
        return new CourseEntity(departureInput.getValue(), arrivalInput.getValue(), 3, routeId);
    }

    @FXML
    void goBack(ActionEvent event) {
        viewManager.switchView("schedule");
    }

}

