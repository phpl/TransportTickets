package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.DriverDao;
import com.transport.entity.DriverEntity;

public class DriversFormLogic {
    private DatabaseService databaseService;
    private DriverDao driverDao;

    public DriversFormLogic() {
        databaseService = new DatabaseService();
        driverDao = new DriverDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    public void addDriver(String firstName,
                          String lastName,
                          int phoneNumber) {
        DriverEntity driverEntity = new DriverEntity(firstName, lastName, phoneNumber);
        driverDao.insertDriver(driverEntity);
    }
}
