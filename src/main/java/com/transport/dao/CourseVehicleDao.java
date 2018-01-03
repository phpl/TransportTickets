package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseVehicleEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class CourseVehicleDao {
    @NonNull
    private DatabaseService databaseService;

    private final String insertNewCourseVechicle = "INSERT INTO " +
            "transport.kurs_pojazd (kurs_pk, pojazd_pk) " +
            "VALUES (?,?);";

    private final String deleteAssociation = "DELETE FROM transport.kurs_pojazd WHERE kurs_pk = ?;";

    public void insertCourseVehicle(CourseVehicleEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(CourseVehicleEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourseVechicle)) {
            log.info("Begin insertNewCourseVehicle");

            preparedStatement.setInt(1, newEntity.getCourseId());
            preparedStatement.setInt(2, newEntity.getVehicleId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourseVehicle");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }

    public void removeAssociation(int courseId) {
        databaseService.setAutoCommit(false);

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteAssociation)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);
    }
}
