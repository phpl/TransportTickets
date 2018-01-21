package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import com.transport.dao.CourseDriverDao;
import com.transport.dao.DriverDao;
import com.transport.view.lists.DriversList;
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

public class DriversController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton usersButton1;

    @FXML
    private JFXButton passengersButton1;

    @FXML
    private JFXButton logoutButton1;

    @FXML
    private JFXButton vehiclesButton;

    @FXML
    private TableView<DriversList> tableView;

    @FXML
    private JFXButton addButton;

    private DatabaseService databaseService;
    private DriverDao driverDao;
    private CourseDriverDao courseDriverDao;

    private ObservableList<DriversList> data = null;

    public void postInit() {
        databaseService = new DatabaseService();
        driverDao = new DriverDao(databaseService);
        courseDriverDao = new CourseDriverDao(databaseService);
        initializeTableView();
        ControllerHelper.resetButtonTexts(null, usersButton1, passengersButton1, vehiclesButton);
        ControllerHelper.hideButtonDependindOnAccountType(null, usersButton1, passengersButton1, vehiclesButton);
    }

    public void dispose() {
        clearTable();
    }

    private void initializeTableView() {
        TableColumn<DriversList, Integer> driverId = new TableColumn<>("Id kierowcy");
        TableColumn<DriversList, String> firstName = new TableColumn<>("Imię");
        TableColumn<DriversList, String> lastName = new TableColumn<>("Nazwisko");
        TableColumn<DriversList, Integer> phoneNumber = new TableColumn<>("Numer telefonu");
        TableColumn<DriversList, Integer> courseId = new TableColumn<>("Id kursu");
        TableColumn delete = new TableColumn("Usuń");

        driverId.setCellValueFactory(
                new PropertyValueFactory<>("driverId")
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
        courseId.setCellValueFactory(
                new PropertyValueFactory<>("courseId")
        );
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<DriversList, String>, TableCell<DriversList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = driverDao.selectAllDrivers();
        tableView.setItems(data);
        tableView.getColumns().addAll(driverId, firstName, lastName, phoneNumber, courseId, delete);
    }

    private Callback<TableColumn<DriversList, String>, TableCell<DriversList, String>> createRemoveButtonTableCellFactory() {
        return new Callback<TableColumn<DriversList, String>, TableCell<DriversList, String>>() {
            @Override
            public TableCell<DriversList, String> call(TableColumn<DriversList, String> param) {
                {
                    return new TableCell<DriversList, String>() {

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
                                    DriversList driver = getTableView().getItems().get(getIndex());

                                    databaseService.setAutoCommit(false);
                                    try {
                                        driverDao.deleteDriverTransaction(driver.getDriverId());
                                        courseDriverDao.deleteAssociationDriver(driver.getDriverId());
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
    void addRecord(ActionEvent event) {
        clearTable();
        viewManager.switchView("driversForm");
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
    void openPassengers(ActionEvent event) {
        clearTable();
        viewManager.switchView("passengers");
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

    private void clearTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }

}
