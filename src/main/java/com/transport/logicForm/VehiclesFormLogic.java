package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.VehicleDao;
import com.transport.entity.VehicleEntity;
import com.transport.view.controllers.ControllerHelper;

import java.sql.SQLException;

public class VehiclesFormLogic {
    private DatabaseService databaseService;
    private VehicleDao vehicleDao;

    public VehiclesFormLogic() {
        databaseService = new DatabaseService();
        vehicleDao = new VehicleDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    public void addVehicle(String model,
                           String licencePlate,
                           int seatsNumber,
                           double luggageWeight) {
        try {
            VehicleEntity vehicleEntity = new VehicleEntity(model, licencePlate, seatsNumber, luggageWeight);
            vehicleDao.insertVechicle(vehicleEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
    }
}
