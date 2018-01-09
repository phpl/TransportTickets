package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseDriverEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class CourseDriverDao extends BasicDao {

    public CourseDriverDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertCourseDriverTransaction(CourseDriverEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(CourseDriverEntity newEntity) throws SQLException {
        String insertNewCourseDriver = "INSERT INTO " +
                "transport.kurs_kierowca (kierowca_pk, kurs_pk) VALUES (?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourseDriver)) {
            log.info("Begin insertNewCourseDriver");

            preparedStatement.setInt(1, newEntity.getDriverId());
            preparedStatement.setInt(2, newEntity.getCourseId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourseDriver");
        }
    }

    public void deleteAssociationDriver(int driverId) throws SQLException {
        String deleteAssociation = "DELETE FROM transport.kurs_kierowca WHERE kierowca_pk = ?";

        executeDeleteAssociation(driverId, deleteAssociation);
    }

    public void deleteAssociationCourse(Integer courseId) throws SQLException {
        String deleteAssociation = "DELETE FROM transport.kurs_kierowca WHERE kurs_pk = ?";

        executeDeleteAssociation(courseId, deleteAssociation);
    }

    private void executeDeleteAssociation(int id, String query) throws SQLException {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(query)) {
            log.info("Begin deleteCourseDriverAssociation");

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            log.info("End deleteCourseDriverAssociation");
        }
    }
}
