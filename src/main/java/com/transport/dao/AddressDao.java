package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.AddressEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class AddressDao {
    private DatabaseService databaseService = null;

    private String insertNewAddress = "INSERT INTO transport.adres (miasto, ulica, numer_domu) VALUES (?, ?, ?);";

    public void insertAddress(AddressEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(AddressEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewAddress)) {
            log.info("Begin insertNewCity");

            preparedStatement.setString(1, newEntity.getCity());
            preparedStatement.setString(2, newEntity.getStreet());
            preparedStatement.setString(3, newEntity.getHouseNumber());
            preparedStatement.executeUpdate();

            log.info("End insertNewCity");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
