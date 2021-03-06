package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.transport.Account;
import com.transport.DatabaseService;
import com.transport.dao.CourseDao;
import com.transport.dao.LuggageDao;
import com.transport.dao.TicketDao;
import com.transport.view.lists.PassengersList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.inject.Inject;
import java.sql.SQLException;

public class PassengersController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton driversButton;

    @FXML
    private JFXButton usersButton1;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton logoutButton1;

    @FXML
    private JFXButton vehiclesButton;

    @FXML
    private TableView<PassengersList> tableView;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXTextField courseId;

    private DatabaseService databaseService;
    private CourseDao courseDao;
    private TicketDao ticketDao;
    private LuggageDao luggageDao;

    private ObservableList<PassengersList> data = null;

    private Integer course;

    public void postInit() {
        databaseService = new DatabaseService();
        courseDao = new CourseDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        luggageDao = new LuggageDao(databaseService);
        clearTable();
        ControllerHelper.resetButtonTexts(driversButton, usersButton1, null, vehiclesButton);
        ControllerHelper.hideButtonDependindOnAccountType(driversButton, usersButton1, null, vehiclesButton);
    }

    public void dispose() {
        clearTable();
    }

    @FXML
    void addRecord(ActionEvent event) {
        clearTable();
        String courseString = courseId.getCharacters().toString();
        course = courseString.isEmpty() ? null :
                Integer.parseInt(courseString) <= 0 ? null : Integer.parseInt(courseString);

        if (course != null && courseDao.checkIfCourseExist(course)) {
            initializeTableView();
        }
    }

    private void initializeTableView() {
        TableColumn<PassengersList, Integer> userId = new TableColumn<>("Id użytkownika");
        TableColumn<PassengersList, String> firstName = new TableColumn<>("Imię");
        TableColumn<PassengersList, String> lastName = new TableColumn<>("Nazwisko");
        TableColumn<PassengersList, Integer> phoneNumber = new TableColumn<>("Numer telefonu");
        TableColumn<PassengersList, Double> luggageWeight = new TableColumn<>("Waga bagażu");
        TableColumn delete = new TableColumn("");

        userId.setCellValueFactory(
                new PropertyValueFactory<>("userId")
        );
        firstName.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        lastName.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        phoneNumber.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber")
        );
        luggageWeight.setCellValueFactory(
                new PropertyValueFactory<>("luggageWeight")
        );
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<PassengersList, String>, TableCell<PassengersList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = ticketDao.selectAllPassenger(course);
        tableView.setItems(data);

        switch (Account.type) {
            case ADMINISTRATOR:
                tableView.getColumns().addAll(userId, firstName, lastName, phoneNumber, luggageWeight, delete);
                break;
            case USER:
                tableView.getColumns().addAll(firstName, lastName);
                break;
        }
    }

    private Callback<TableColumn<PassengersList, String>, TableCell<PassengersList, String>> createRemoveButtonTableCellFactory() {
        return new Callback<TableColumn<PassengersList, String>, TableCell<PassengersList, String>>() {
            @Override
            public TableCell<PassengersList, String> call(TableColumn<PassengersList, String> param) {
                {
                    return new TableCell<PassengersList, String>() {

                        final Button btn = new Button("Usuń");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction((ActionEvent event) ->
                                {
                                    PassengersList passengers = getTableView().getItems().get(getIndex());
                                    databaseService.setAutoCommit(false);
                                    try {
                                        luggageDao.deleteLuggageTransaction(passengers.getUserId(), passengers.getTicketId());
                                        ticketDao.deleteTicketTransaction(passengers.getUserId(), passengers.getCourseId());
                                        databaseService.commitTransaction();
                                        data.remove(getIndex());
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        databaseService.rollbackTransaction();
                                        ControllerHelper.showErrorAlertMessage(e.getMessage());
                                    }
                                    databaseService.setAutoCommit(true);
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                }
            }
        };
    }

    @FXML
    void openVehicles(ActionEvent event) {
        clearTable();
        viewManager.switchView("vehicles");
    }

    @FXML
    void logout(ActionEvent event) {
        clearTable();
        viewManager.switchView("login");
    }

    @FXML
    void openSchedules(ActionEvent event) {
        clearTable();
        viewManager.switchView("schedule");
    }

    @FXML
    void openUsers(ActionEvent event) {
        clearTable();
        viewManager.switchView("users");
    }

    @FXML
    void goBack(ActionEvent event) {
        clearTable();
        viewManager.switchView("main");
    }

    @FXML
    void openDrivers(ActionEvent event) {
        clearTable();
        viewManager.switchView("drivers");
    }

    private void clearTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }
}
