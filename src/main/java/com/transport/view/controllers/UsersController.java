package com.transport.view.controllers;

import com.gluonhq.particle.view.ViewManager;
import com.jfoenix.controls.JFXButton;
import com.transport.DatabaseService;
import com.transport.dao.UserDao;
import com.transport.view.lists.ScheduleList;
import com.transport.view.lists.UsersList;
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

public class UsersController {

    @Inject
    private ViewManager viewManager;

    @FXML
    private JFXButton schedulesButton;

    @FXML
    private JFXButton driversButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton passengersButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private TableView<UsersList> tableView;

    private DatabaseService databaseService;
    private UserDao userDao;

    private ObservableList<UsersList> data = null;

    public void postInit() {
        databaseService = new DatabaseService();
        userDao = new UserDao(databaseService);
        databaseService.connectToDatabase();
        initializeTableView();
    }

    private void initializeTableView() {
        TableColumn<UsersList, Integer> userId = new TableColumn<>("Id");
        TableColumn<UsersList, String> username = new TableColumn<>("Użytkownik");
        TableColumn<UsersList, String> firstName = new TableColumn<>("Imię");
        TableColumn<UsersList, String> lastName = new TableColumn<>("Nazwisko");
        TableColumn<UsersList, Integer> phoneNumber = new TableColumn<>("Numer telefonu");
        TableColumn<UsersList, String> city = new TableColumn<>("Miasto");
        TableColumn<UsersList, String> street = new TableColumn<>("Ulica");
        TableColumn<UsersList, String> houseNumber = new TableColumn<>("Numer domu");
        TableColumn delete = new TableColumn("Akcja");

        userId.setCellValueFactory(
                new PropertyValueFactory<>("userId")
        );
        username.setCellValueFactory(
                new PropertyValueFactory<>("username")
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
        city.setCellValueFactory(
                new PropertyValueFactory<>("city")
        );
        street.setCellValueFactory(
                new PropertyValueFactory<>("street")
        );
        houseNumber.setCellValueFactory(
                new PropertyValueFactory<>("houseNumber")
        );
        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<ScheduleList, String>, TableCell<ScheduleList, String>> removeButtonFactory =
                createRemoveButtonTableCellFactory();
        delete.setCellFactory(removeButtonFactory);

        data = userDao.selectAllUsers();
        tableView.setItems(data);
        tableView.getColumns().addAll(userId, username, firstName, lastName, phoneNumber, city, street, houseNumber, delete);
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
//TODO add delete user
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
    void openSchedules(ActionEvent event) {
        clearTable();
        viewManager.switchView("schedule");
    }

    private void clearTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }
}
