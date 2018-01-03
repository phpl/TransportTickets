package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.TicketEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class TicketDao {
    @NonNull
    private DatabaseService databaseService;

    private final String isnertNewTicket = "INSERT INTO " +
            "transport.bilet (uzytkownik_pk, cena, kurs_pk) " +
            "VALUES (?, ?, ?);";

    private final String deleteTicket = "DELETE FROM transport.bilet WHERE kurs_pk = ?;";

    public void insertTicket(TicketEntity newEntity) throws SQLException {
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

    public void removeTicket(int courseId) {
        databaseService.setAutoCommit(false);

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteTicket)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);
    }
}
