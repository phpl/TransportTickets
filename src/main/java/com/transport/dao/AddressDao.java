package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.AddressEntity;
import com.transport.view.controllers.ControllerHelper;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class AddressDao extends BasicDao {

    private final String insertNewAddress = "INSERT INTO transport.adres (miasto, ulica, numer_domu) VALUES (?, ?, ?);";

    private final String selectIdFromAddress = "SELECT adres.adres_pk FROM transport.adres" +
            " WHERE miasto = ? AND ulica = ? AND numer_domu = ?;";

    public AddressDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertAddress(AddressEntity newEntity) {
        try {
            executeInsert(newEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            ControllerHelper.errorWhileRecordAdd();
        }
    }

    private void executeInsert(AddressEntity newEntity) throws SQLException {
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
