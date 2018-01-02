package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.DriverEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class DriverDao {
    private DatabaseService databaseService = null;

    private String insertNewDriver = "INSERT INTO " +
            "transport.kierowca (imie, nazwisko, numer_telefonu, termin_waznosci_badan, termin_waznosci_prawa_jazdy) " +
            "VALUES (?, ?, ?, ?, ?);";

    public void insertDriver(DriverEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(DriverEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewDriver)) {
            log.info("Begin insertNewDriver");

            preparedStatement.setString(1, newEntity.getFirstName());
            preparedStatement.setString(2, newEntity.getLastName());
            preparedStatement.setInt(1, newEntity.getPhoneNumber());
            preparedStatement.setObject(1, newEntity.getPeriodOfMedicalCheckUpValidation());
            preparedStatement.setObject(2, newEntity.getPeriodOfDriverLicenceValidation());
            preparedStatement.executeUpdate();

            log.info("End insertNewDriver");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
