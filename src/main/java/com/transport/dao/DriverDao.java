package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.DriverEntity;
import com.transport.view.controllers.ControllerHelper;
import com.transport.view.lists.DriversList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class DriverDao extends BasicDao {

    public DriverDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertDriver(DriverEntity newEntity) {
        try {
            executeInsert(newEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            ControllerHelper.errorWhileRecordAdd();
        }
    }

    private void executeInsert(DriverEntity newEntity) throws SQLException {
        String insertNewDriver = "INSERT INTO " +
                "transport.kierowca (imie, nazwisko, numer_telefonu) " +
                "VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewDriver)) {
            log.info("Begin insertNewDriver");

            preparedStatement.setString(1, newEntity.getFirstName());
            preparedStatement.setString(2, newEntity.getLastName());
            preparedStatement.setInt(3, newEntity.getPhoneNumber());
            preparedStatement.executeUpdate();

            log.info("End insertNewDriver");
        }
    }

    public int getDriverId(int phoneNumber) {
        ResultSet resultSet;
        int idOfElement = -1;
        String selectIdFromDriver = "SELECT kierowca.kierowca_pk FROM transport.kierowca" +
                " WHERE numer_telefonu = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromDriver)) {

            preparedStatement.setInt(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }


        return idOfElement;
    }

    public ObservableList<DriversList> selectAllDrivers() {
        ResultSet resultSet;
        ObservableList<DriversList> data = FXCollections.observableArrayList();
        String selectFromDrivers = "SELECT * FROM transport.kierowca " +
                "LEFT JOIN transport.kurs_kierowca ON kierowca.kierowca_pk = kurs_kierowca.kierowca_pk;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromDrivers)) {
            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }


        return data;
    }

    private ObservableList<DriversList> retrieveData(ResultSet resultSet) throws SQLException {
        ObservableList<DriversList> data = FXCollections.observableArrayList();
        DriversList drivers;

        while (resultSet.next()) {
            drivers = new DriversList(
                    resultSet.getInt("kierowca_pk"),
                    resultSet.getString("imie"),
                    resultSet.getString("nazwisko"),
                    resultSet.getInt("numer_telefonu"),
                    resultSet.getInt("kurs_pk")
            );
            data.add(drivers);
        }

        return data;
    }
}
