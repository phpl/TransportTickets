package com.transport.dao;

import com.transport.DatabaseService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
@NoArgsConstructor
public class BasicDao {

    @NonNull
    protected DatabaseService databaseService;

    public void removeFromDatabase(int idOfEntity, String statement) {
        databaseService.setAutoCommit(false);

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setInt(1, idOfEntity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);
    }

    public int getIntFromEntity(String value, String statement) {
        ResultSet resultSet;

        int intValue = -1;

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {

            preparedStatement.setString(1, value);
            resultSet = preparedStatement.executeQuery();
            intValue = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);

        return intValue;
    }

    public int retrieveId(ResultSet resultSet) throws SQLException {
        int idOfElement = -1;

        if (resultSet.next()) {
            idOfElement = resultSet.getInt(1);
        }

        return idOfElement;
    }
}
