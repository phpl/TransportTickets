package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.*;
import com.transport.entity.CourseDriverEntity;
import com.transport.entity.CourseEntity;
import com.transport.entity.CourseVehicleEntity;
import com.transport.entity.RouteEntity;
import com.transport.exceptions.DatabaseException;
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
        databaseService.setAutoCommit(false);
        try {
            RouteEntity routeEntity = new RouteEntity(distance, beginCity, endCity);
            routeDao.insertRoute(routeEntity);

            CourseEntity courseEntity = createCourseEntity(routeEntity, departureTime, arrivalTime, licencePlate);
            courseDao.insertCourse(courseEntity);


            CourseVehicleEntity courseVehicleEntity = createCourseVehicleEntity(courseEntity, licencePlate);
            courseVehicleDao.insertCourseVehicle(courseVehicleEntity);

            CourseDriverEntity courseDriverEntity = createCourseDriverEntity(courseEntity, phoneNumber);
            courseDriverDao.insertCourseDriver(courseDriverEntity);

        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);

    }

    private CourseEntity createCourseEntity(RouteEntity routeEntity,
                                            LocalTime departureTime,
                                            LocalTime arrivalTime,
                                            String licencePlate) throws DatabaseException {
        int routeId = routeDao.getRouteIdOfItem(routeEntity);
        int seatsNumber = vehicleDao.getSeatsNumber(licencePlate);

        validateIds(routeId);
        validateIds(seatsNumber);

        return new CourseEntity(departureTime, arrivalTime, seatsNumber, routeId);
    }

    private CourseVehicleEntity createCourseVehicleEntity(CourseEntity courseEntity,
                                                          String licencePlate) throws DatabaseException {
        int courseId = courseDao.getCourseId(courseEntity);
        int vehicleId = vehicleDao.getVechicleId(licencePlate);

        validateIds(courseId);
        validateIds(vehicleId);

        return new CourseVehicleEntity(courseId, vehicleId);
    }

    private CourseDriverEntity createCourseDriverEntity(CourseEntity courseEntity,
                                                        int phoneNumber) throws DatabaseException {
        int courseId = courseDao.getCourseId(courseEntity);
        int driverId = driverDao.getDriverId(phoneNumber);

        validateIds(courseId);
        validateIds(driverId);

        return new CourseDriverEntity(courseId, driverId);
    }

    private void validateIds(int id) throws DatabaseException {
        if (id == -1) {
            throw new DatabaseException();
        }
    }
}
