package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.UserEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class UserDao {
    @NonNull
    private DatabaseService databaseService;

    private String insertNewUser = "INSERT INTO " +
            "transport.uzytkownik (login, haslo) " +
            "VALUES (?, ?);";

    public void insertUser(UserEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(UserEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewUser)) {
            log.info("Begin insertNewUser");

            preparedStatement.setString(1, newEntity.getLogin());
            preparedStatement.setString(2, newEntity.getPassword());
            preparedStatement.executeUpdate();

            log.info("End insertNewUser");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
