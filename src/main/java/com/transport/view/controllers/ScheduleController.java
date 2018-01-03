package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.transport.DatabaseService;
import com.transport.dao.CourseDao;
import com.transport.dao.CourseDriverDao;
import com.transport.dao.CourseVehicleDao;
import com.transport.dao.TicketDao;
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
    private Button backButton;

    @FXML
    private Button driversButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button passengersButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addButton;

    @FXML
    private TableView<ScheduleList> tableView;

    private DatabaseService databaseService;
    private CourseDao courseDao;
    private CourseDriverDao courseDriverDao;
    private CourseVehicleDao courseVehicleDao;
    private TicketDao ticketDao;

    private ObservableList<ScheduleList> data = null;

    public void postInit() {
        databaseService = new DatabaseService();
        courseDao = new CourseDao(databaseService);
        courseDriverDao = new CourseDriverDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        ticketDao = new TicketDao(databaseService);
        databaseService.connectToDatabase();
        initializeTableView();
    }

    private void initializeTableView() {
        TableColumn<ScheduleList, String> city = new TableColumn<>("Miasto");
        TableColumn<ScheduleList, String> departureTime = new TableColumn<>("Godzina odjazdu");
        TableColumn<ScheduleList, String> freeSeats = new TableColumn<>("Wolne miejsca");
        TableColumn<ScheduleList, String> distance = new TableColumn<>("Odległość");
        TableColumn<ScheduleList, String> ticketPrice = new TableColumn<>("Cena biletu");
        TableColumn delete = new TableColumn("Akcja");

        city.setCellValueFactory(
                new PropertyValueFactory<>("city")
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
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = courseDao.selectAllCourses();
        tableView.setItems(data);
        tableView.getColumns().addAll(city, departureTime, freeSeats, distance, ticketPrice, delete);
    }

    private Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> createRemoveButtonTableCellFactory() {
        return new Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>>() {
            @Override
            public TableCell<ScheduleList, String> call(TableColumn<ScheduleList, String> param) {
                {
                    return new TableCell<ScheduleList, String>() {

                        final Button btn = new Button("Remove");

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
//TODO add missing delete methods
                                    courseDriverDao.removeAssociation(schedule.getCourseId());
//                                    courseVehicleDao.removeAssociation(schedule.getCourseId());
//                                    ticketDao.removeTicket(schedule.getCourseId());
//                                    courseDao.removeCourse(schedule.getCourseId());
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

    @FXML
    void addRecord(ActionEvent event) {

    }

    @FXML
    void backButton(ActionEvent event) {
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
