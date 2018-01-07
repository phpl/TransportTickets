package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.TicketEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class TicketDao extends BasicDao {

    private final String isnertNewTicket = "INSERT INTO " +
            "transport.bilet (uzytkownik_pk, cena, kurs_pk) " +
            "VALUES (?, ?, ?);";

    private final String deleteTicket = "DELETE FROM transport.bilet WHERE kurs_pk = ?;";

    private final String selectIdFromTicket = "SELECT bilet.bilet_pk FROM transport.bilet" +
            " WHERE uzytkownik_pk = ? AND kurs_pk = ?;";

    public TicketDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertTicket(TicketEntity newEntity) {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(TicketEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(isnertNewTicket)) {
            log.info("Begin insertNewTicket");

            preparedStatement.setInt(1, newEntity.getUserId());
            preparedStatement.setDouble(2, newEntity.getPrice());
            preparedStatement.setInt(3, newEntity.getCourseId());
            preparedStatement.executeUpdate();

            log.info("End insertNewTicket");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }

    public void removeAssociation(int courseId) {
        removeFromDatabase(courseId, deleteTicket);
    }

    public int getTicketId(TicketEntity entityToFind) {
        ResultSet resultSet;

        int idOfElement = -1;

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromTicket)) {

            preparedStatement.setInt(1, entityToFind.getUserId());
            preparedStatement.setInt(2, entityToFind.getCourseId());
            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);

        return idOfElement;
    }
}
