package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.PersonalDataEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class PersonalDataDao extends BasicDao {

    public PersonalDataDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertPersonalDataTransaction(PersonalDataEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(PersonalDataEntity newEntity) throws SQLException {
        String insertNewPersonalData = "INSERT INTO " +
                "transport.dane_osobowe (uzytkownik_pk, imie, nazwisko, numer_telefonu, adres_pk) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewPersonalData)) {
            log.info("Begin insertNewPersonalData");

            preparedStatement.setInt(1, newEntity.getUserId());
            preparedStatement.setString(2, newEntity.getFirstName());
            preparedStatement.setString(3, newEntity.getLastName());
            preparedStatement.setInt(4, newEntity.getPhoneNumber());
            preparedStatement.setInt(5, newEntity.getAddresId());
            preparedStatement.executeUpdate();

            log.info("End insertNewPersonalData");
        }
    }

    public void deletePersonalDataTransaction(int userId, int addressId) throws SQLException {
        String deletePersonalData = "DELETE FROM transport.dane_osobowe WHERE uzytkownik_pk = ? AND adres_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deletePersonalData)) {
            log.info("Begin deletePersonalData");

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, addressId);
            preparedStatement.executeUpdate();

            log.info("End deletePersonalData");
        }
    }
}
