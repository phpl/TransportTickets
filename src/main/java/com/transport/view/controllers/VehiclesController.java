package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import com.transport.dao.CourseVehicleDao;
import com.transport.dao.VehicleDao;
import com.transport.view.lists.VehiclesList;
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

public class VehiclesController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton scheduleButton;

    @FXML
    private JFXButton driversButton;

    @FXML
    private JFXButton usersButton1;

    @FXML
    private JFXButton passengersButton1;

    @FXML
    private JFXButton logoutButton1;

    @FXML
    private JFXButton backButton;

    @FXML
    private TableView<VehiclesList> tableView;

    @FXML
    private JFXButton addButton;

    private DatabaseService databaseService;
    private VehicleDao vehicleDao;
    private CourseVehicleDao courseVehicleDao;

    private ObservableList<VehiclesList> data = null;

    public void postInit() {
        ControllerHelper.resetButtonTexts(driversButton, usersButton1, passengersButton1, null);
        ControllerHelper.hideButtonDependindOnAccountType(driversButton, usersButton1, passengersButton1, null);
        databaseService = new DatabaseService();
        vehicleDao = new VehicleDao(databaseService);
        courseVehicleDao = new CourseVehicleDao(databaseService);
        initializeTableView();
    }

    public void dispose() {
        clearTable();
    }

    private void initializeTableView() {
        TableColumn<VehiclesList, Integer> vehicleId = new TableColumn<>("Id pojazdu");
        TableColumn<VehiclesList, String> model = new TableColumn<>("Model");
        TableColumn<VehiclesList, String> licencePlate = new TableColumn<>("Numer rejestracji");
        TableColumn<VehiclesList, Integer> seatsNumber = new TableColumn<>("Ilość miejsc");
        TableColumn<VehiclesList, Integer> courseId = new TableColumn<>("Id kursu");
        TableColumn delete = new TableColumn("Usuń");

        vehicleId.setCellValueFactory(
                new PropertyValueFactory<>("vehicleId")
        );
        model.setCellValueFactory(
                new PropertyValueFactory<>("model")
        );
        licencePlate.setCellValueFactory(
                new PropertyValueFactory<>("licencePlate")
        );
        seatsNumber.setCellValueFactory(
                new PropertyValueFactory<>("seatsNumber")
        );
        courseId.setCellValueFactory(
                new PropertyValueFactory<>("courseId")
        );
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<VehiclesList, String>, TableCell<VehiclesList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = vehicleDao.selectAllVehicles();
        tableView.setItems(data);
        tableView.getColumns().addAll(vehicleId, model, licencePlate, seatsNumber, courseId, delete);
    }

    private Callback<TableColumn<VehiclesList, String>, TableCell<VehiclesList, String>> createRemoveButtonTableCellFactory() {
        return new Callback<TableColumn<VehiclesList, String>, TableCell<VehiclesList, String>>() {
            @Override
            public TableCell<VehiclesList, String> call(TableColumn<VehiclesList, String> param) {
                {
                    return new TableCell<VehiclesList, String>() {

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
                                    VehiclesList vehicle = getTableView().getItems().get(getIndex());

                                    databaseService.setAutoCommit(false);
                                    try {
                                        vehicleDao.deleteVehicleTransaction(vehicle.getVehicleId());
                                        courseVehicleDao.deleteAssociationVehicle(vehicle.getVehicleId());
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
        viewManager.switchView("vehiclesForm");
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
