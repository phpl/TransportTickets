package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CityEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.transport.queries.CityQuerries.INSERT_NEW_CITY;

@Log4j
public class CityDao {

    private DatabaseService databaseService = null;

    public CityDao(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void insertNewCity(CityEntity cityEntityToInsert) throws SQLException {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(INSERT_NEW_CITY)) {

            log.info("Begin INSERT_NEW_CITY");

            preparedStatement.setString(1, cityEntityToInsert.getName());

            preparedStatement.executeUpdate();

            log.info("End INSERT_NEW_CITY");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
