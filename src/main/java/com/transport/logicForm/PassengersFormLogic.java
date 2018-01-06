package com.transport.logicForm;

import com.transport.DatabaseService;

public class PassengersFormLogic {
    private DatabaseService databaseService;

    public PassengersFormLogic() {
        databaseService = new DatabaseService();
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }
}
