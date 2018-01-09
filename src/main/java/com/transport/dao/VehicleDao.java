package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.VehicleEntity;
import com.transport.view.controllers.ControllerHelper;
import com.transport.view.lists.VehiclesList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class VehicleDao extends BasicDao {

    private final String insertNewVechicle = "INSERT INTO " +
            "transport.pojazd (model, numer_rejestracji, ilosc_miejsc) " +
            "VALUES (?, ?, ?);";

    private final String selectIdFromVechicle = "SELECT pojazd.pojazd_pk FROM transport.pojazd" +
            " WHERE numer_rejestracji = ?;";

    private final String selectSeatNumberFromVechicle = "SELECT pojazd.ilosc_miejsc FROM transport.pojazd" +
            " WHERE numer_rejestracji = ?;";

    private final String selectFromVehicles = "SELECT * FROM transport.pojazd " +
            "LEFT JOIN transport.kurs_pojazd ON pojazd.pojazd_pk = kurs_pojazd.pojazd_pk;";

    public VehicleDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertVehicle(VehicleEntity newEntity) {
        try {
            executeInsert(newEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            ControllerHelper.errorWhileRecordAdd();
        }
    }

    private void executeInsert(VehicleEntity newEntity) throws SQLException {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewVechicle)) {
            log.info("Begin insertNewVechicle");

            preparedStatement.setString(1, newEntity.getModel());
            preparedStatement.setString(2, newEntity.getLicencePlate());
            preparedStatement.setInt(3, newEntity.getSeatsNumber());
            preparedStatement.executeUpdate();

            log.info("End insertNewVechicle");
        }
    }

    public int getSeatsNumber(String licencePlate) {
        return getIntFromEntity(licencePlate, selectSeatNumberFromVechicle);
    }

    public int getVehicleId(String licencePlate) {
        return getIntFromEntity(licencePlate, selectIdFromVechicle);
    }

    public ObservableList<VehiclesList> selectAllVehicles() {
        ResultSet resultSet;
        ObservableList<VehiclesList> data = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromVehicles)) {
            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private ObservableList<VehiclesList> retrieveData(ResultSet resultSet) throws SQLException {
        ObservableList<VehiclesList> data = FXCollections.observableArrayList();
        VehiclesList vehicles;

        while (resultSet.next()) {
            vehicles = new VehiclesList(
                    resultSet.getInt("pojazd_pk"),
                    resultSet.getString("model"),
                    resultSet.getString("numer_rejestracji"),
                    resultSet.getInt("ilosc_miejsc"),
                    resultSet.getInt("kurs_pk")
            );
            data.add(vehicles);
        }

        return data;
    }
}
