package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.VehicleDao;
import com.transport.entity.VehicleEntity;

public class VehiclesFormLogic {
    private DatabaseService databaseService;
    private VehicleDao vehicleDao;

    public VehiclesFormLogic() {
        databaseService = new DatabaseService();
        vehicleDao = new VehicleDao(databaseService);
    }

    public void dispose() {
    }

    public void addVehicle(String model,
                           String licencePlate,
                           int seatsNumber) {
        VehicleEntity vehicleEntity = new VehicleEntity(model, licencePlate, seatsNumber);
        vehicleDao.insertVehicle(vehicleEntity);
    }
}
