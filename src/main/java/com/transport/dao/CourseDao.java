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

    public CourseDao(DatabaseService databaseService) {
        super(databaseService);
    }

    public void insertCourseTransaction(CourseEntity newEntity) throws SQLException {
        executeInsert(newEntity);
    }

    private void executeInsert(CourseEntity newEntity) throws SQLException {
        String insertNewCourse = "INSERT INTO " +
                "transport.kurs (godzina_odjazdu, godzina_powrotu, maks_dostepna_ilosc_miejsc, trasa_pk) " +
                "VALUES (?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertNewCourse)) {
            log.info("Begin insertNewCourse");
            preparedStatement.setTime(1, java.sql.Time.valueOf(newEntity.getDepartureTime()));
            preparedStatement.setTime(2, java.sql.Time.valueOf(newEntity.getArrivalTime()));
            preparedStatement.setInt(3, newEntity.getMaxAvailableSeats());
            preparedStatement.setInt(4, newEntity.getRouteId());
            preparedStatement.executeUpdate();

            log.info("End insertNewCourse");
        }
    }

    public ObservableList<ScheduleList> selectAllCourses() {
        ResultSet resultSet;
        ObservableList<ScheduleList> data = FXCollections.observableArrayList();
        String selectFromCourseView = "SELECT * FROM transport.trasy_view;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectFromCourseView)) {
            resultSet = preparedStatement.executeQuery();
            data = retrieveData(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

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

    public int getCourseId(CourseEntity entityToFind) {
        ResultSet resultSet;
        int idOfElement = -1;
        String selectIdFromCourse = "SELECT kurs.kurs_pk FROM transport.kurs" +
                " WHERE kurs.godzina_odjazdu = ? AND kurs.godzina_powrotu = ? AND maks_dostepna_ilosc_miejsc = ? AND trasa_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(selectIdFromCourse)) {

            preparedStatement.setTime(1, java.sql.Time.valueOf(entityToFind.getDepartureTime()));
            preparedStatement.setTime(2, java.sql.Time.valueOf(entityToFind.getArrivalTime()));
            preparedStatement.setInt(3, entityToFind.getMaxAvailableSeats());
            preparedStatement.setInt(4, entityToFind.getRouteId());
            resultSet = preparedStatement.executeQuery();
            idOfElement = retrieveId(resultSet);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return idOfElement;
    }

    public boolean checkIfCourseExist(int courseId) {
        ResultSet resultSet;
        boolean exist = false;
        String checkCourse = "SELECT * FROM transport.kurs WHERE kurs_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(checkCourse)) {

            preparedStatement.setInt(1, courseId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                exist = true;
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public void deleteCourseTransaction(Integer courseId) throws SQLException {
        String deleteCourse = "DELETE FROM transport.kurs WHERE kurs_pk = ?;";

        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteCourse)) {
            log.info("Begin deleteCourse");

            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();

            log.info("End deleteCourse");
        }
    }
}

