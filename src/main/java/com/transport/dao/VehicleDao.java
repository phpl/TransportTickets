package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.VehicleEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class VehicleDao extends BasicDao {

    private final String insertNewVechicle = "INSERT INTO " +
            "transport.pojazd (model, numer_rejestracji, ilosc_miejsc, dopuszczalny_bagaz) " +
            "VALUES (?, ?, ?, ?);";

    private final String selectIdFromVechicle = "SELECT pojazd.pojazd_pk FROM transport.pojazd" +
            " WHERE numer_rejestracji = ?;";

    private final String selectSeatNumberFromVechicle = "SELECT pojazd.ilosc_miejsc FROM transport.pojazd" +
            " WHERE numer_rejestracji = ?;";

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

    public int getSeatsNumber(String licencePlate) {
        return getIntFromVehicle(licencePlate, selectSeatNumberFromVechicle);
    }

    public int getVechicleId(String licencePlate) {
        return getIntFromVehicle(licencePlate, selectIdFromVechicle);
    }


    private int getIntFromVehicle(String licencePlate, String statement) {
        ResultSet resultSet;

        int intValue = -1;

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {

            preparedStatement.setString(1, licencePlate);
            resultSet = preparedStatement.executeQuery();
            intValue = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);

        return intValue;
    }

    private int retrieveId(ResultSet resultSet) throws SQLException {
        int idOfElement = -1;

        if (resultSet.next()) {
            idOfElement = resultSet.getInt(1);
        }

        return idOfElement;
    }
}
