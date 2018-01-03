package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CityEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class CityDao {
    @NonNull
    private DatabaseService databaseService;

    private final String insertNewCity = "INSERT INTO transport.miasto (nazwa) VALUES (?);";

    public void insertCity(CityEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(CityEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCity)) {
            log.info("Begin insertNewCity");

            preparedStatement.setString(1, newEntity.getName());
            preparedStatement.executeUpdate();

            log.info("End insertNewCity");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
