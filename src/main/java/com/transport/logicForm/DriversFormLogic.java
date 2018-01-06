package com.transport.logicForm;

import com.transport.DatabaseService;

public class DriversFormLogic {
    private DatabaseService databaseService;

    public DriversFormLogic() {
        databaseService = new DatabaseService();
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }
}
