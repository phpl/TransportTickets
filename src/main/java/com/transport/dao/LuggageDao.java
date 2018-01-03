package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.LuggageEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class LuggageDao {
    @NonNull
    private DatabaseService databaseService;

    private final String insertNewDriver = "INSERT INTO " +
            "transport.bagaz (bilet_pk, waga, uzytkownik_pk) " +
            "VALUES (?, ?, ?);";

    public void insertLuggage(LuggageEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(LuggageEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewDriver)) {
            log.info("Begin insertNewLuggage");

            preparedStatement.setInt(1, newEntity.getTicketId());
            preparedStatement.setDouble(2, newEntity.getWeight());
            preparedStatement.setInt(3, newEntity.getUserId());
            preparedStatement.executeUpdate();

            log.info("End insertNewLuggage");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
