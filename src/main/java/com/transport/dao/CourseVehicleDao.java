package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseVehicleEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class CourseVehicleDao extends BasicDao {

    public CourseVehicleDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertCourseVehicleTransaction(CourseVehicleEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(CourseVehicleEntity newEntity) throws SQLException {
        String insertNewCourseVechicle = "INSERT INTO " +
                "transport.kurs_pojazd (kurs_pk, pojazd_pk) " +
                "VALUES (?,?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourseVechicle)) {
            log.info("Begin insertNewCourseVehicle");

            preparedStatement.setInt(1, newEntity.getCourseId());
            preparedStatement.setInt(2, newEntity.getVehicleId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourseVehicle");
        }
    }

    public void deleteAssociation(int vehicleId) throws SQLException {
        String deleteAssociation = "DELETE FROM transport.kurs_pojazd WHERE pojazd_pk = ?";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteAssociation)) {
            log.info("Begin deleteCourseVehicleAssociation");

            preparedStatement.setInt(1, vehicleId);
            preparedStatement.executeUpdate();

            log.info("End deleteCourseVehicleAssociation");
        }
    }
}
