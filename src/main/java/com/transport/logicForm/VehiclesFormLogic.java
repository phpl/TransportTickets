package com.transport.logicForm;

import com.transport.DatabaseService;

public class VehiclesFormLogic {
    private DatabaseService databaseService;

    public VehiclesFormLogic() {
        databaseService = new DatabaseService();
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }
}
