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

    public void insertAddress(AddressEntity newEntity) throws SQLException {
        String insertNewAddress = "INSERT INTO transport.miasto (nazwa) VALUES (?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewAddress)) {
            log.info("Begin insertNewAddress");

//            preparedStatement.setString(1, newEntity.getName());
            preparedStatement.executeUpdate();

            log.info("End insertNewAddress");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
