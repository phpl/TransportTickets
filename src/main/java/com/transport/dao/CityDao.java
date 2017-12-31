package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CityEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class CityDao {
    private DatabaseService databaseService = null;

    private String insertNewCity = "INSERT INTO transport.miasto (nazwa) VALUES (?);";

    public void insertCity(CityEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(false);
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
