package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseVehicleEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class CourseVehicleDao extends BasicDao {

    private final String insertNewCourseVechicle = "INSERT INTO " +
            "transport.kurs_pojazd (kurs_pk, pojazd_pk) " +
            "VALUES (?,?);";

    private final String deleteAssociation = "DELETE FROM transport.kurs_pojazd WHERE kurs_pk = ?;";

    public CourseVehicleDao(DatabaseService databaseService) {
        super(databaseService);
    }

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
        removeFromDatabase(courseId, deleteAssociation);
    }
}
