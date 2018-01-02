package com.transport.view.controllers;

import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.state.StateManager;
import com.gluonhq.particle.view.ViewManager;
import com.transport.DatabaseService;
import com.transport.dao.LuggageDao;
import com.transport.entity.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import lombok.extern.log4j.Log4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;

import javax.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

@Log4j
public class PrimaryController {

    @Inject
    ParticleApplication app;

    @Inject
    private ViewManager viewManager;

    @Inject
    private StateManager stateManager;

    private boolean first = true;

    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    private ResourceBundle resources;

    private Action actionSignin;

    private DatabaseService databaseService;

    public void initialize() {
        ActionMap.register(this);
        actionSignin = ActionMap.action("signin");

        button.setOnAction(e -> viewManager.switchView("secondary"));
    }

    public void postInit() {
        databaseService = new DatabaseService();
        if (first) {
            stateManager.setPersistenceMode(StateManager.PersistenceMode.USER);
            addUser(stateManager.getProperty("UserName").orElse("").toString());
            first = false;
        }
        app.getParticle().getToolBarActions().add(0, actionSignin);
    }

    public void dispose() {
        app.getParticle().getToolBarActions().remove(actionSignin);
    }

    public void addUser(String userName) {
        label.setText(resources.getString("label.text") + (userName.isEmpty() ? "" : ", " + userName) + "!");
        stateManager.setProperty("UserName", userName);

        testInserts();
        databaseService.closeConnection();
    }

    private void testInserts() {
        databaseService.connectToDatabase();
//        AddressDao addressDao = new AddressDao(databaseService);
//        UserDao userDao = new UserDao(databaseService);
//        CourseVehicleDao courseVehicleDao = new CourseVehicleDao(databaseService);
//        TicketDao ticketDao = new TicketDao(databaseService);
//        VehicleDao vehicleDao = new VehicleDao(databaseService);
//        CityDao cityDao = new CityDao(databaseService);
//        CourseDao courseDao = new CourseDao(databaseService);
//        CourseDriverDao courseDriverDao = new CourseDriverDao(databaseService);
//        DriverDao driverDao = new DriverDao(databaseService);
        LuggageDao luggageDao = new LuggageDao(databaseService);
//        PersonalDataDao personalDataDao = new PersonalDataDao(databaseService);
//        RouteCityDao routeCityDao = new RouteCityDao(databaseService);
//        RouteDao routeDao = new RouteDao(databaseService);

        AddressEntity addressEntity = new AddressEntity("test", "test", "test");
        UserEntity userEntity = new UserEntity("test", "test");
        CourseVehicleEntity courseVehicleEntity = new CourseVehicleEntity(2, 1);
        TicketEntity ticketEntity = new TicketEntity(1, 1.0, 2);
        VehicleEntity vehicleEntity = new VehicleEntity("test", "test", 1, 1.0);
        CityEntity cityEntity = new CityEntity("test");
        CourseEntity courseEntity = new CourseEntity(LocalTime.of(1, 0), LocalTime.of(1, 1), 1, 1);
        CourseDriverEntity courseDriverEntity = new CourseDriverEntity(1, 2);
        DriverEntity driverEntity = new DriverEntity("test", "test", 1, LocalDate.now(), LocalDate.now());
        LuggageEntity luggageEntity = new LuggageEntity(3, 1, 1);
        PersonalDataEntity personalDataEntity = new PersonalDataEntity(1, "test", "test", 1, 1);
        RouteCityEntity routeCityEntity = new RouteCityEntity(1, 1);
        RouteEntity routeEntity = new RouteEntity(1);

//        try {
//            addressDao.insertAddress(addressEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            userDao.insertUser(userEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            courseVehicleDao.insertCourseVehicle(courseVehicleEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            ticketDao.insertTicket(ticketEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            vehicleDao.insertVechicle(vehicleEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            cityDao.insertCity(cityEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            courseDao.insertCourse(courseEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            courseDriverDao.insertCourseDriver(courseDriverEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            driverDao.insertDriver(driverEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            luggageDao.insertLuggage(luggageEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try {
//            personalDataDao.insertPersonalData(personalDataEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            routeCityDao.insertRouteCity(routeCityEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            routeDao.insertRoute(routeEntity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @ActionProxy(text = "Zaloguj sie")
    private void signin() {
        TextInputDialog input = new TextInputDialog(stateManager.getProperty("Uzytkownik").orElse("").toString());
        input.setTitle("UserEntity name");
        input.setHeaderText(null);
        input.setContentText("Input your name:");
        input.showAndWait().ifPresent(this::addUser);
    }


}