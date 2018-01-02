package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class CourseDao {
    private DatabaseService databaseService = null;

    private String insertNewCourse = "INSERT INTO " +
            "transport.kurs (godzina_odjazdu, godzina_powrotu, maks_dostepna_ilosc_miejsc, trasa_pk) VALUES (?, ?, ?);";

    public void insertCourse(CourseEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(CourseEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourse)) {
            log.info("Begin insertNewCourse");

            preparedStatement.setObject(1, newEntity.getDepartureTime());
            preparedStatement.setObject(2, newEntity.getArrivalTime());
            preparedStatement.setInt(1, newEntity.getMaxAvailableSeats());
            preparedStatement.setInt(2, newEntity.getRouteId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourse");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
