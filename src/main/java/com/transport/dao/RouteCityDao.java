package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.RouteCityEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class RouteCityDao {
    @NonNull
    private DatabaseService databaseService;

    private String insertNewRouteCity = "INSERT INTO " +
            "transport.trasa_miasto (trasa_pk, miasto_pk) " +
            "VALUES (?, ?);";

    public void isnertRouteCity(RouteCityEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(RouteCityEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewRouteCity)) {
            log.info("Begin insertNewRouteCity");

            preparedStatement.setInt(1, newEntity.getRouteId());
            preparedStatement.setInt(2, newEntity.getCityId());
            preparedStatement.executeUpdate();

            log.info("End insertNewRouteCIty");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
