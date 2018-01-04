package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.RouteEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class RouteDao extends BasicDao {

    private final String insertNewRoute = "INSERT INTO " +
            "transport.trasa (odleglosc, miasto_poczatkowe, miasto_koncowe) " +
            "VALUES (?,?,?);";

    public RouteDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertRoute(RouteEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(RouteEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewRoute)) {
            log.info("Begin insertNewRoute");

            preparedStatement.setInt(1, newEntity.getDistance());
            preparedStatement.setString(2, newEntity.getBeginCity());
            preparedStatement.setString(3, newEntity.getEndCity());
            preparedStatement.executeUpdate();

            log.info("End insertNewRoute");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
