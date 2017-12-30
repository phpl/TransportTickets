package com.transport.controller;

import com.transport.model.City;
import com.transport.DatabaseService;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.transport.queries.CityQuerries.INSERT_NEW_CITY;

public class CityBean {
    private static Logger logger = Logger.getLogger(CityBean.class);

    private DatabaseService databaseService = null;

    public CityBean(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void insertNewCity(City cityToInsert) throws SQLException {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(INSERT_NEW_CITY)) {

            logger.info("Begin INSERT_NEW_CITY");

            preparedStatement.setString(1, cityToInsert.getName());

            preparedStatement.executeUpdate();

            logger.info("End INSERT_NEW_CITY");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
