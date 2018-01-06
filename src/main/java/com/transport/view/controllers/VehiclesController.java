package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import com.transport.dao.VehicleDao;
import com.transport.view.lists.ScheduleList;
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
    VehicleDao vehicleDao;

    private ObservableList<VehiclesList> data = null;

    public void postInit() {
        databaseService = new DatabaseService();
        vehicleDao = new VehicleDao(databaseService);
        databaseService.connectToDatabase();
        initializeTableView();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    private void initializeTableView() {
        TableColumn<VehiclesList, Integer> vehicleId = new TableColumn<>("Id pojazdu");
        TableColumn<VehiclesList, String> model = new TableColumn<>("Model");
        TableColumn<VehiclesList, String> licencePlate = new TableColumn<>("Numer rejestracji");
        TableColumn<VehiclesList, Integer> seatsNumber = new TableColumn<>("Ilość miejsc");
        TableColumn<VehiclesList, Double> luggageWeight = new TableColumn<>("Dopuszczalna waga bagażu");
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
        luggageWeight.setCellValueFactory(
                new PropertyValueFactory<>("luggageWeight")
        );
        courseId.setCellValueFactory(
                new PropertyValueFactory<>("courseId")
        );
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = vehicleDao.selectAllVehicles();
        tableView.setItems(data);
        tableView.getColumns().addAll(vehicleId, model, licencePlate, seatsNumber, luggageWeight, courseId, delete);
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
//TODO add delete vehicle
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
