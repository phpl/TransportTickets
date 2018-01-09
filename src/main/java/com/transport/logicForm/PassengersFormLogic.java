package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.LuggageDao;
import com.transport.dao.TicketDao;
import com.transport.entity.LuggageEntity;
import com.transport.entity.TicketEntity;
import com.transport.exceptions.DatabaseException;
import com.transport.view.controllers.ControllerHelper;

import java.sql.SQLException;

public class PassengersFormLogic {
    private DatabaseService databaseService;
    private LuggageDao luggageDao;
    private TicketDao ticketDao;

    public PassengersFormLogic() {
        databaseService = new DatabaseService();
        luggageDao = new LuggageDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    public void addPassenger(Integer passengerId, Integer courseId, Double price, Double luggageWeight) {
        databaseService.setAutoCommit(false);

        try {
            validateIfNullValue(passengerId);
            validateIfNullValue(courseId);
            validateIfNullValue(price);

            TicketEntity ticketEntity = new TicketEntity(passengerId, price, courseId);
            ticketDao.insertTicketTransaction(ticketEntity);

            addLuggage(luggageWeight, ticketEntity, passengerId);

            databaseService.commitTransaction();

        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }

        databaseService.setAutoCommit(true);
    }

    private void validateIfNullValue(Object value) throws DatabaseException {
        if (value == null) {
            throw new DatabaseException();
        }
    }

    private void addLuggage(Double luggageWeight, TicketEntity ticketEntity, Integer passengerId) throws DatabaseException, SQLException {
        if (luggageWeight != null) {
            int ticketId = ticketDao.getTicketId(ticketEntity);
            ControllerHelper.validateIds(ticketId);
            LuggageEntity luggageEntity = new LuggageEntity(ticketId, luggageWeight, passengerId);
            luggageDao.insertLuggageTransaction(luggageEntity);
        }
    }
}
