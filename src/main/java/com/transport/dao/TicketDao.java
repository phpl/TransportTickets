package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.TicketEntity;
import com.transport.view.lists.PassengersList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class TicketDao extends BasicDao {

    public TicketDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertTicketTransaction(TicketEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(TicketEntity newEntity) throws SQLException {
        String isnertNewTicket = "INSERT INTO " +
                "transport.bilet (uzytkownik_pk, cena, kurs_pk) " +
                "VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(isnertNewTicket)) {
            log.info("Begin insertNewTicket");

            preparedStatement.setInt(1, newEntity.getUserId());
            preparedStatement.setDouble(2, newEntity.getPrice());
            preparedStatement.setInt(3, newEntity.getCourseId());
            preparedStatement.executeUpdate();

            log.info("End insertNewTicket");
        }
    }

    public int getTicketId(TicketEntity entityToFind) {
        ResultSet resultSet;
        int idOfElement = -1;
        String selectIdFromTicket = "SELECT bilet.bilet_pk FROM transport.bilet" +
                " WHERE uzytkownik_pk = ? AND kurs_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromTicket)) {

            preparedStatement.setInt(1, entityToFind.getUserId());
            preparedStatement.setInt(2, entityToFind.getCourseId());
            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return idOfElement;
    }

    public ObservableList<PassengersList> selectAllPassenger(int courseId) {
        ResultSet resultSet;
        ObservableList<PassengersList> data = FXCollections.observableArrayList();
        String selectFromPassengersView = "SELECT  * FROM transport.pasazerowie_view WHERE kurs_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromPassengersView)) {
            preparedStatement.setInt(1, courseId);

            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private ObservableList<PassengersList> retrieveData(ResultSet resultSet) throws SQLException {
        ObservableList<PassengersList> data = FXCollections.observableArrayList();
        PassengersList passengers;

        while (resultSet.next()) {
            passengers = new PassengersList(
                    resultSet.getInt("uzytkownik_pk"),
                    resultSet.getInt("bilet_pk"),
                    resultSet.getInt("bagaz_pk"),
                    resultSet.getInt("kurs_pk"),
                    resultSet.getString("imie"),
                    resultSet.getString("nazwisko"),
                    resultSet.getInt("numer_telefonu"),
                    resultSet.getDouble("waga")
            );
            data.add(passengers);
        }

        return data;
    }

    public void deleteTicketTransaction(Integer userId, Integer courseId) throws SQLException {
        String deleteTicket = "DELETE FROM transport.bilet WHERE uzytkownik_pk = ? AND kurs_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteTicket)) {
            log.info("Begin deleteTicket");

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();

            log.info("End deleteTicket");
        }
    }
}
