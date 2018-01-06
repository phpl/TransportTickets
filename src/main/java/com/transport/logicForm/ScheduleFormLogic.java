package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.*;
import com.transport.entity.CourseDriverEntity;
import com.transport.entity.CourseEntity;
import com.transport.entity.CourseVehicleEntity;
import com.transport.entity.RouteEntity;
import com.transport.view.controllers.ControllerHelper;

import java.sql.SQLException;
import java.time.LocalTime;

public class ScheduleFormLogic {

    private DatabaseService databaseService;
    private CourseDao courseDao;
    private CourseDriverDao courseDriverDao;
    private DriverDao driverDao;
    private CourseVehicleDao courseVehicleDao;
    private VehicleDao vehicleDao;
    private TicketDao ticketDao;
    private RouteDao routeDao;

    public ScheduleFormLogic() {
        databaseService = new DatabaseService();
        courseDao = new CourseDao(databaseService);
        courseDriverDao = new CourseDriverDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        routeDao = new RouteDao(databaseService);
        driverDao = new DriverDao(databaseService);
        vehicleDao = new VehicleDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    public void insertCourseView(String beginCity,
                                 String endCity,
                                 int distance,
                                 LocalTime departureTime,
                                 LocalTime arrivalTime,
                                 int phoneNumber,
                                 String licencePlate
    ) {
        RouteEntity routeEntity = new RouteEntity(distance, beginCity, endCity);
        insertRoute(routeEntity);

        CourseEntity courseEntity = createCourseEntity(routeEntity, departureTime, arrivalTime, licencePlate);
        insertCourse(courseEntity);

        CourseVehicleEntity courseVehicleEntity = createCourseVehicleEntity(courseEntity, licencePlate);
        insertCourseVehicle(courseVehicleEntity);

        CourseDriverEntity courseDriverEntity = createCourseDriverEntity(courseEntity, phoneNumber);
        insertCourseDriver(courseDriverEntity);
    }

    private void insertRoute(RouteEntity routeEntity) {
        databaseService.setAutoCommit(false);
        try {
            routeDao.insertRoute(routeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }

    private void insertCourse(CourseEntity courseEntity) {
        databaseService.setAutoCommit(false);
        try {
            courseDao.insertCourse(courseEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }

    private void insertCourseVehicle(CourseVehicleEntity courseVehicleEntity) {
        databaseService.setAutoCommit(false);
        try {
            courseVehicleDao.insertCourseVehicle(courseVehicleEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }

    private void insertCourseDriver(CourseDriverEntity courseDriverEntity) {
        databaseService.setAutoCommit(false);
        try {
            courseDriverDao.insertCourseDriver(courseDriverEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }

    private CourseEntity createCourseEntity(RouteEntity routeEntity,
                                            LocalTime departureTime,
                                            LocalTime arrivalTime,
                                            String licencePlate) {
        int routeId = routeDao.getRouteIdOfItem(routeEntity);
        int seatsNumber = vehicleDao.getSeatsNumber(licencePlate);

        return new CourseEntity(departureTime, arrivalTime, seatsNumber, routeId);
    }

    private CourseVehicleEntity createCourseVehicleEntity(CourseEntity courseEntity,
                                                          String licencePlate) {
        int courseId = courseDao.getCourseId(courseEntity);
        int vehicleId = vehicleDao.getVechicleId(licencePlate);

        return new CourseVehicleEntity(courseId, vehicleId);
    }

    private CourseDriverEntity createCourseDriverEntity(CourseEntity courseEntity,
                                                        int phoneNumber) {
        int courseId = courseDao.getCourseId(courseEntity);
        int driverId = driverDao.getDriverId(phoneNumber);

        return new CourseDriverEntity(courseId, driverId);
    }
}
