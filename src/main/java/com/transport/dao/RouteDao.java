package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.RouteEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class RouteDao extends BasicDao {

    private final String insertNewRoute = "INSERT INTO " +
            "transport.trasa (odleglosc, miasto_poczatkowe, miasto_koncowe) " +
            "VALUES (?,?,?);";

    private final String selectIdFromRoute = "SELECT trasa.trasa_pk FROM transport.trasa" +
            " WHERE trasa.odleglosc = ? AND trasa.miasto_poczatkowe = ? AND trasa.miasto_koncowe = ?;";

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

    public int getRouteIdOfItem(RouteEntity entityToFind) {
        ResultSet resultSet;

        int idOfElement = -1;

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromRoute)) {
            preparedStatement.setInt(1, entityToFind.getDistance());
            preparedStatement.setString(2, entityToFind.getBeginCity());
            preparedStatement.setString(3, entityToFind.getEndCity());

            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);

        return idOfElement;
    }
}
