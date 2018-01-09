package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.PersonalDataEntity;
import com.transport.view.controllers.ControllerHelper;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class PersonalDataDao extends BasicDao {

    private final String insertNewPersonalData = "INSERT INTO " +
            "transport.dane_osobowe (uzytkownik_pk, imie, nazwisko, numer_telefonu, adres_pk) " +
            "VALUES (?, ?, ?, ?, ?);";

    public PersonalDataDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertPersonalData(PersonalDataEntity newEntity) {
        try {
            executeInsert(newEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            ControllerHelper.errorWhileRecordAdd();
        }
    }

    private void executeInsert(PersonalDataEntity newEntity) throws SQLException {
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
}
