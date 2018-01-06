package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import com.transport.dao.*;
import com.transport.view.lists.ScheduleList;
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

public class ScheduleController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private TableView<ScheduleList> tableView;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton driversButton1;

    @FXML
    private JFXButton usersButton1;

    @FXML
    private JFXButton passengersButton1;

    @FXML
    private JFXButton logoutButton1;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton vehiclesButton;

    private DatabaseService databaseService;
    private CourseDao courseDao;
    private CourseDriverDao courseDriverDao;
    private CourseVehicleDao courseVehicleDao;
    private TicketDao ticketDao;
    private LuggageDao luggageDao;

    private ObservableList<ScheduleList> data = null;

    public void postInit() {
        databaseService = new DatabaseService();
        courseDao = new CourseDao(databaseService);
        courseDriverDao = new CourseDriverDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        luggageDao = new LuggageDao(databaseService);
        databaseService.connectToDatabase();
        initializeTableView();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    private void initializeTableView() {
        TableColumn<ScheduleList, String> beginCity = new TableColumn<>("Miasto poczatkowe");
        TableColumn<ScheduleList, String> endCity = new TableColumn<>("Miasto koncowe");
        TableColumn<ScheduleList, String> departureTime = new TableColumn<>("Godzina odjazdu");
        TableColumn<ScheduleList, String> freeSeats = new TableColumn<>("Wolne miejsca");
        TableColumn<ScheduleList, String> distance = new TableColumn<>("Odległość");
        TableColumn<ScheduleList, String> ticketPrice = new TableColumn<>("Cena biletu");
        TableColumn add = new TableColumn("");
        TableColumn delete = new TableColumn("");

        beginCity.setCellValueFactory(
                new PropertyValueFactory<>("beginCity")
        );
        endCity.setCellValueFactory(
                new PropertyValueFactory<>("endCity")
        );
        departureTime.setCellValueFactory(
                new PropertyValueFactory<>("departureTime")
        );
        freeSeats.setCellValueFactory(
                new PropertyValueFactory<>("freeSeats")
        );
        distance.setCellValueFactory(
                new PropertyValueFactory<>("distance")
        );
        ticketPrice.setCellValueFactory(
                new PropertyValueFactory<>("ticketPrice")
        );
        add.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> addButtonFactory =
                createAddButtonTabCellFactory();
        add.setCellFactory(addButtonFactory);

        Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = courseDao.selectAllCourses();
        tableView.setItems(data);
        tableView.getColumns().addAll(beginCity, endCity, departureTime, freeSeats, distance, ticketPrice, add, delete);
    }

    private Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> createRemoveButtonTableCellFactory() {
        return new Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>>() {
            @Override
            public TableCell<ScheduleList, String> call(TableColumn<ScheduleList, String> param) {
                {
                    return new TableCell<ScheduleList, String>() {

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
                                    ScheduleList schedule = getTableView().getItems().get(getIndex());
//                                    TODO add trigger deleting all luggages for specific course
                                    data.remove(getIndex());
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

    private Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> createAddButtonTabCellFactory() {
        return new Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>>() {
            @Override
            public TableCell<ScheduleList, String> call(TableColumn<ScheduleList, String> param) {
                {
                    return new TableCell<ScheduleList, String>() {

                        final Button btn = new Button("Dodaj pasażera");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction((ActionEvent event) ->
                                {
                                    ScheduleList schedule = getTableView().getItems().get(getIndex());
//TODO add kup bilet
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
    void addRecord(ActionEvent event) {
        clearTable();
        viewManager.switchView("scheduleForm");
    }

    @FXML
    void goBack(ActionEvent event) {
        clearTable();
        viewManager.switchView("main");
    }

    @FXML
    void logout(ActionEvent event) {
        clearTable();
        viewManager.switchView("login");
    }

    @FXML
    void openDrivers(ActionEvent event) {
        clearTable();
        viewManager.switchView("drivers");
    }

    @FXML
    void openPassengers(ActionEvent event) {
        clearTable();
        viewManager.switchView("passengers");
    }

    @FXML
    void openUsers(ActionEvent event) {
        clearTable();
        viewManager.switchView("users");
    }

    private void clearTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }
}
