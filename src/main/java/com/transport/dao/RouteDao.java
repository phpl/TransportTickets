package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.RouteEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class RouteDao extends BasicDao {

    public RouteDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertRouteTransaction(RouteEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(RouteEntity newEntity) throws SQLException {
        String insertNewRoute = "INSERT INTO " +
                "transport.trasa (odleglosc, miasto_poczatkowe, miasto_koncowe) " +
                "VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewRoute)) {
            log.info("Begin insertNewRoute");

            preparedStatement.setInt(1, newEntity.getDistance());
            preparedStatement.setString(2, newEntity.getBeginCity());
            preparedStatement.setString(3, newEntity.getEndCity());
            preparedStatement.executeUpdate();

            log.info("End insertNewRoute");
        }
    }

    public int getRouteIdOfItem(RouteEntity entityToFind) {
        ResultSet resultSet;
        int idOfElement = -1;
        String selectIdFromRoute = "SELECT trasa.trasa_pk FROM transport.trasa" +
                " WHERE trasa.odleglosc = ? AND trasa.miasto_poczatkowe = ? AND trasa.miasto_koncowe = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromRoute)) {
            preparedStatement.setInt(1, entityToFind.getDistance());
            preparedStatement.setString(2, entityToFind.getBeginCity());
            preparedStatement.setString(3, entityToFind.getEndCity());

            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return idOfElement;
    }

    public void deleteRouteTransaction(Integer routeId) throws SQLException {
        String deleteRoute = "DELETE FROM transport.trasa WHERE trasa_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteRoute)) {
            log.info("Begin deleteRoute");

            preparedStatement.setInt(1, routeId);
            preparedStatement.executeUpdate();

            log.info("End deleteRoute");
        }
    }
}
