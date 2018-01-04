package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXTimePicker;
import com.transport.DatabaseService;
import com.transport.dao.*;
import com.transport.entity.CourseEntity;
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
    private JFXTimePicker departureInput;

    @FXML
    private JFXTimePicker arrivalInput;

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

//        CourseEntity courseEntity = new CourseEntity(departureInput.getCharacters(), arrivalInput.getCharacters(), 2, );
//        courseDao.insertCourse();
        //kurs, trasa, kierowca_trasa, pojazd
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

