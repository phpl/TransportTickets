package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.AddressEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class AddressDao extends BasicDao {

    public AddressDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertAddressTransaction(AddressEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(AddressEntity newEntity) throws SQLException {
        String insertNewAddress = "INSERT INTO transport.adres (miasto, ulica, numer_domu) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewAddress)) {
            log.info("Begin insertNewAddress");

            preparedStatement.setString(1, newEntity.getCity());
            preparedStatement.setString(2, newEntity.getStreet());
            preparedStatement.setString(3, newEntity.getHouseNumber());
            preparedStatement.executeUpdate();

            log.info("End insertNewAddress");
        }
    }

    public int getAddressId(AddressEntity entityToFind) {
        ResultSet resultSet;
        int idOfElement = -1;
        String selectIdFromAddress = "SELECT adres.adres_pk FROM transport.adres" +
                " WHERE miasto = ? AND ulica = ? AND numer_domu = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromAddress)) {

            preparedStatement.setString(1, entityToFind.getCity());
            preparedStatement.setString(2, entityToFind.getStreet());
            preparedStatement.setString(3, entityToFind.getHouseNumber());
            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return idOfElement;
    }
}
