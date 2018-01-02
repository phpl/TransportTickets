package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.RouteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class RouteDao {
    private DatabaseService databaseService = null;

    private String insertNewRoute = "INSERT INTO " +
            "transport.trasa (odleglosc) " +
            "VALUES (?);";

    public void isnertRoute(RouteEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(RouteEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewRoute)) {
            log.info("Begin insertNewRoute");

            preparedStatement.setInt(1, newEntity.getDistance());
            preparedStatement.executeUpdate();

            log.info("End insertNewRoute");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
