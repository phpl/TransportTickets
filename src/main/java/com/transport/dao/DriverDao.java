package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.DriverEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class DriverDao {
    @NonNull
    private DatabaseService databaseService;

    private final String insertNewDriver = "INSERT INTO " +
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
            preparedStatement.setInt(3, newEntity.getPhoneNumber());
            preparedStatement.setObject(4, newEntity.getPeriodOfMedicalCheckUpValidation());
            preparedStatement.setObject(5, newEntity.getPeriodOfDriverLicenceValidation());
            preparedStatement.executeUpdate();

            log.info("End insertNewDriver");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
