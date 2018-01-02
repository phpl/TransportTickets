package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseDriverEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j
@RequiredArgsConstructor
public class CourseDriverDao {
    private DatabaseService databaseService = null;

    private String insertNewCourseDriver = "INSERT INTO " +
            "transport.kurs_kierowca (kierowca_pk, kurs_pk) VALUES (?, ?);";

    public void insertCourseDriver(CourseDriverEntity newEntity) throws SQLException {
        databaseService.setAutoCommit(false);
        executeInsert(newEntity);
        databaseService.setAutoCommit(true);
    }

    private void executeInsert(CourseDriverEntity newEntity) {
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourseDriver)) {
            log.info("Begin insertNewCourseDriver");

            preparedStatement.setInt(1, newEntity.getDriverId());
            preparedStatement.setInt(2, newEntity.getCourseId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourseDriver");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }
}
