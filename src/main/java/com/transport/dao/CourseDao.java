package com.transport.dao;

import com.transport.DatabaseService;
import com.transport.entity.CourseEntity;
import com.transport.view.lists.ScheduleList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class CourseDao extends BasicDao {

    private final String insertNewCourse =
            "INSERT INTO " +
                    "transport.kurs (godzina_odjazdu, godzina_powrotu, maks_dostepna_ilosc_miejsc, trasa_pk) " +
                    "VALUES (?, ?, ?, ?);";

    private final String selectFromCourseView = "SELECT * FROM transport.trasy_view";

    private final String deleteCourse = "DELETE FROM transport.kurs WHERE kurs_pk = ?;";

    public CourseDao(DatabaseService databaseService) {
        super(databaseService);
    }

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
            preparedStatement.setInt(3, newEntity.getMaxAvailableSeats());
            preparedStatement.setInt(4, newEntity.getRouteId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourse");
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }
    }

    public ObservableList<ScheduleList> selectAllCourses() {
        ResultSet resultSet;
        ObservableList<ScheduleList> data = FXCollections.observableArrayList();

        databaseService.setAutoCommit(false);
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromCourseView)) {
            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
        }

        databaseService.setAutoCommit(true);

        return data;
    }

    private ObservableList<ScheduleList> retrieveData(ResultSet resultSet) throws SQLException {
        ObservableList<ScheduleList> data = FXCollections.observableArrayList();
        ScheduleList schedules;

        while (resultSet.next()) {
            schedules = new ScheduleList(
                    resultSet.getInt("kurs_pk"),
                    resultSet.getInt("trasa_pk"),
                    resultSet.getString("miasto_poczatkowe"),
                    resultSet.getString("miasto_koncowe"),
                    resultSet.getTime("godzina_odjazdu").toLocalTime(),
                    resultSet.getInt("wolne_miejsca"),
                    resultSet.getInt("km"),
                    resultSet.getInt("cena_biletu")
            );
            data.add(schedules);
        }

        return data;
    }

    public void removeCourse(int courseId) {
        removeFromDatabase(courseId, deleteCourse);
    }
}

