package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.VehicleEntity;
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
            "transport.pojazd (model, numer_rejestracji, ilosc_miejsc, dopuszczalny_bagaz) " +
            "VALUES (?, ?, ?, ?);";

    private final String selectIdFromVechicle = "SELECT pojazd.pojazd_pk FROM transport.pojazd" +
            " WHERE numer_rejestracji = ?;";

    private final String selectSeatNumberFromVechicle = "SELECT pojazd.ilosc_miejsc FROM transport.pojazd" +
            " WHERE numer_rejestracji = ?;";

    private final String selectFromVehicles = "SELECT * FROM transport.pojazd " +
            "LEFT JOIN transport.kurs_pojazd ON pojazd.pojazd_pk = kurs_pojazd.pojazd_pk;";

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
        return getIntFromEntity(licencePlate, selectSeatNumberFromVechicle);
    }

    public int getVechicleId(String licencePlate) {
        return getIntFromEntity(licencePlate, selectIdFromVechicle);
    }

    public ObservableList<VehiclesList> selectAllVehicles() {
        ResultSet resultSet;
        ObservableList<VehiclesList> data = FXCollections.observableArrayList();

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromVehicles)) {
            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);

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
                    resultSet.getDouble("dopuszczalny_bagaz"),
                    resultSet.getInt("kurs_pk")
            );
            data.add(vehicles);
        }

        return data;
    }
}
