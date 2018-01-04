package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.VehicleEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class VehicleDao extends BasicDao {

    private final String insertNewVechicle = "INSERT INTO " +
            "transport.pojazd (model, numer_rejestracji, ilosc_miejsc, dopuszczalny_bagaz) " +
            "VALUES (?, ?, ?, ?);";

    public VehicleDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertVechicle(VehicleEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(VehicleEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewVechicle)) {
            log.info("Begin insertNewVechicle");

            preparedStatement.setString(1, newEntity.getModel());
            preparedStatement.setString(2, newEntity.getLicencePlate());
            preparedStatement.setInt(3, newEntity.getSeatsNumber());
            preparedStatement.setDouble(4, newEntity.getAcceptableLuggageWeight());
            preparedStatement.executeUpdate();

            log.info("End insertNewVechicle");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
