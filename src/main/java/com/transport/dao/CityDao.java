package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CityEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class CityDao {

    private DatabaseService databaseService = null;

    public CityDao(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void insertNewCity(CityEntity cityEntityToInsert) throws SQLException {
        String insertNewCity = "INSERT INTO transport.miasto (nazwa) VALUES (?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCity)) {
            log.info("Begin insertNewCity");

            preparedStatement.setString(1, cityEntityToInsert.getName());
            preparedStatement.executeUpdate();

            log.info("End insertNewCity");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
