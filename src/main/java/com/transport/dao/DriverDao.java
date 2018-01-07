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

    private final String insertNewDriver = "INSERT INTO " +
            "transport.kierowca (imie, nazwisko, numer_telefonu) " +
            "VALUES (?, ?, ?);";

    private final String selectIdFromDriver = "SELECT kierowca.kierowca_pk FROM transport.kierowca" +
            " WHERE numer_telefonu = ?;";

    private final String selectFromDrivers = "SELECT * FROM transport.kierowca " +
            "LEFT JOIN transport.kurs_kierowca ON kierowca.kierowca_pk = kurs_kierowca.kierowca_pk;";

    public DriverDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertDriver(DriverEntity newEntity) {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(DriverEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewDriver)) {
            log.info("Begin insertNewDriver");

            preparedStatement.setString(1, newEntity.getFirstName());
            preparedStatement.setString(2, newEntity.getLastName());
            preparedStatement.setInt(3, newEntity.getPhoneNumber());
            preparedStatement.executeUpdate();

            log.info("End insertNewDriver");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
    }

    public int getDriverId(int phoneNumber) {
        ResultSet resultSet;

        int idOfElement = -1;

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromDriver)) {

            preparedStatement.setInt(1, phoneNumber);
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

    public ObservableList<DriversList> selectAllDrivers() {
        ResultSet resultSet;
        ObservableList<DriversList> data = FXCollections.observableArrayList();

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromDrivers)) {
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
