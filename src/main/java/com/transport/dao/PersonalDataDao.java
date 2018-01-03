package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.PersonalDataEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class PersonalDataDao {
    @NonNull
    private DatabaseService databaseService;

    private final String insertNewPersonalData = "INSERT INTO " +
            "transport.dane_osobowe (uzytkownik_pk, imie, nazwisko, numer_telefonu, adres_pk) " +
            "VALUES (?, ?, ?, ?, ?);";

    public void insertPersonalData(PersonalDataEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(PersonalDataEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewPersonalData)) {
            log.info("Begin insertNewPersonalData");

            preparedStatement.setInt(1, newEntity.getUserId());
            preparedStatement.setString(2, newEntity.getFirstName());
            preparedStatement.setString(3, newEntity.getLastName());
            preparedStatement.setInt(4, newEntity.getPhoneNumber());
            preparedStatement.setInt(5, newEntity.getAddresId());
            preparedStatement.executeUpdate();

            log.info("End insertNewPersonalData");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
