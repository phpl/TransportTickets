package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseDriverEntity;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
public class CourseDriverDao extends BasicDao {

    private final String insertNewCourseDriver = "INSERT INTO " +
            "transport.kurs_kierowca (kierowca_pk, kurs_pk) VALUES (?, ?);";

    private final String deleteAssociation = "DELETE FROM transport.kurs_kierowca WHERE kurs_pk = ?;";

    public CourseDriverDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertCourseDriverTransaction(CourseDriverEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(CourseDriverEntity newEntity) throws SQLException {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourseDriver)) {
            log.info("Begin insertNewCourseDriver");

            preparedStatement.setInt(1, newEntity.getDriverId());
            preparedStatement.setInt(2, newEntity.getCourseId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourseDriver");
        }
    }
}
