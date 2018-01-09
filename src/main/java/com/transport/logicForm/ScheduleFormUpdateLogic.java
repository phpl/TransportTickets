package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.CourseDriverDao;
import com.transport.dao.CourseVehicleDao;
import com.transport.dao.DriverDao;
import com.transport.dao.VehicleDao;
import com.transport.entity.CourseDriverEntity;
import com.transport.entity.CourseVehicleEntity;
import com.transport.exceptions.DatabaseException;
import com.transport.view.controllers.ControllerHelper;

import java.sql.SQLException;

public class ScheduleFormUpdateLogic {
    private DatabaseService databaseService;
    private CourseDriverDao courseDriverDao;
    private CourseVehicleDao courseVehicleDao;
    private DriverDao driverDao;
    private VehicleDao vehicleDao;

    public ScheduleFormUpdateLogic() {
        databaseService = new DatabaseService();
        courseDriverDao = new CourseDriverDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        driverDao = new DriverDao(databaseService);
        vehicleDao = new VehicleDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    public void updateCourse(Integer courseId, int phoneNumber, String licencePlate) {
        databaseService.setAutoCommit(false);

        try {
            CourseVehicleEntity courseVehicleEntity = createCourseVehicleEntity(courseId, licencePlate);
            courseVehicleDao.insertCourseVehicleTransaction(courseVehicleEntity);

            CourseDriverEntity courseDriverEntity = createCourseDriverEntity(courseId, phoneNumber);
            courseDriverDao.insertCourseDriverTransaction(courseDriverEntity);

            databaseService.commitTransaction();
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.showErrorAlertMessage(e.getMessage());
        }

        databaseService.setAutoCommit(true);
    }

    private CourseVehicleEntity createCourseVehicleEntity(Integer courseId,
                                                          String licencePlate) throws DatabaseException {
        int vehicleId = vehicleDao.getVehicleId(licencePlate);

        ControllerHelper.validateIds(vehicleId);

        return new CourseVehicleEntity(courseId, vehicleId);
    }

    private CourseDriverEntity createCourseDriverEntity(Integer courseId,
                                                        int phoneNumber) throws DatabaseException {
        int driverId = driverDao.getDriverId(phoneNumber);
        ControllerHelper.validateIds(driverId);

        return new CourseDriverEntity(courseId, driverId);
    }
}
