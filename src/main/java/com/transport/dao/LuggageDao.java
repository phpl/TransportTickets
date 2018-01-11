package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.LuggageEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class LuggageDao extends BasicDao {

    public LuggageDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertLuggageTransaction(LuggageEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(LuggageEntity newEntity) throws SQLException {
        String insertNewDriver = "INSERT INTO " +
                "transport.bagaz (bilet_pk, waga, uzytkownik_pk) " +
                "VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewDriver)) {
            log.info("Begin insertNewLuggage");

            preparedStatement.setInt(1, newEntity.getTicketId());
            preparedStatement.setDouble(2, newEntity.getWeight());
            preparedStatement.setInt(3, newEntity.getUserId());
            preparedStatement.executeUpdate();

            log.info("End insertNewLuggage");
        }
    }

    public void deleteLuggageTransaction(Integer userId, Integer ticketId) throws SQLException {
        String deleteLuggage = "DELETE FROM transport.bagaz WHERE uzytkownik_pk = ? AND bilet_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteLuggage)) {
            log.info("Begin deleteLuggage");

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, ticketId);
            preparedStatement.executeUpdate();

            log.info("End deleteLuggage");
        }
    }
}
