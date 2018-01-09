package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.UserEntity;
import com.transport.view.lists.UsersList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class UserDao extends BasicDao {

    private final String selectIdFromUser = "SELECT uzytkownik.uzytkownik_pk FROM transport.uzytkownik" +
            " WHERE login = ?;";

    public UserDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertUserTransaction(UserEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(UserEntity newEntity) throws SQLException {
        String insertNewUser = "INSERT INTO " +
                "transport.uzytkownik (login, haslo) " +
                "VALUES (?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewUser)) {
            log.info("Begin insertNewUser");

            preparedStatement.setString(1, newEntity.getLogin());
            preparedStatement.setString(2, newEntity.getPassword());
            preparedStatement.executeUpdate();

            log.info("End insertNewUser");
        }
    }

    public int getUserId(String username) {
        return getIntFromEntity(username, selectIdFromUser);
    }

    public boolean checkIfUserExist(String username) {
        return getIntFromEntity(username, selectIdFromUser) != -1;
    }

    public ObservableList<UsersList> selectAllUsers() {
        ResultSet resultSet;
        ObservableList<UsersList> data = FXCollections.observableArrayList();
        String selectFromUsersView = "SELECT * FROM transport.uzytkownicy_view";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromUsersView)) {
            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private ObservableList<UsersList> retrieveData(ResultSet resultSet) throws SQLException {
        ObservableList<UsersList> data = FXCollections.observableArrayList();
        UsersList users;

        while (resultSet.next()) {
            users = new UsersList(
                    resultSet.getInt("uzytkownik_pk"),
                    resultSet.getInt("dane_pk"),
                    resultSet.getInt("adres_pk"),
                    resultSet.getString("login"),
                    resultSet.getString("imie"),
                    resultSet.getString("nazwisko"),
                    resultSet.getInt("numer_telefonu"),
                    resultSet.getString("miasto"),
                    resultSet.getString("ulica"),
                    resultSet.getString("numer_domu"));
            data.add(users);
        }
        return data;
    }

    public boolean checkUserCredentials(String username, String password) {
        ResultSet resultSet;
        boolean canLogin = false;
        String checkUser = "SELECT uzytkownik.uzytkownik_pk FROM transport.uzytkownik" +
                " WHERE login = ? AND haslo = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(checkUser)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                canLogin = true;
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return canLogin;
    }

    public void deleteUserTransaction(int userId) throws SQLException {
        String deleteUser = "DELETE FROM transport.uzytkownik WHERE uzytkownik_pk = ?";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteUser)) {
            log.info("Begin deleteUser");

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            log.info("End deleteUser");
        }
    }
}