package com.transport.view.controllers;

import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.state.StateManager;
import com.gluonhq.particle.view.ViewManager;

import java.sql.SQLException;
import java.util.ResourceBundle;

import com.transport.controller.CityBean;
import com.transport.model.City;
import com.transport.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;

public class PrimaryController {

    @Inject ParticleApplication app;
    
    @Inject private ViewManager viewManager;

    @Inject private StateManager stateManager;
    
    private boolean first = true;
    
    @FXML
    private Label label;
    
    @FXML
    private Button button;
    
    @FXML
    private ResourceBundle resources;
    
    private Action actionSignin;

    private DatabaseService databaseService;

    private CityBean cityBean;

    private static Logger logger = Logger.getLogger(PrimaryController.class);


    public void initialize() {
        ActionMap.register(this);
        actionSignin =  ActionMap.action("signin");
        
        button.setOnAction(e -> viewManager.switchView("secondary"));


    }
    
    public void postInit() {

        databaseService = new DatabaseService();
        cityBean = new CityBean(databaseService);
        if (first) {
            stateManager.setPersistenceMode(StateManager.PersistenceMode.USER);
            addUser(stateManager.getProperty("UserName").orElse("").toString());
            first = false;
        }
        app.getParticle().getToolBarActions().add(0, actionSignin);
    }
    
    public void dispose() {
        app.getParticle().getToolBarActions().remove(actionSignin);
    }
    
    public void addUser(String userName) {
        label.setText(resources.getString("label.text") + (userName.isEmpty() ? "" :  ", " + userName) + "!");
        stateManager.setProperty("UserName", userName);

        databaseService.connectToDatabase();
        City city = new City(userName);
        try {
            cityBean.insertNewCity(city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @ActionProxy(text="Sign In")
    private void signin() {
        TextInputDialog input = new TextInputDialog(stateManager.getProperty("UserName").orElse("").toString());
        input.setTitle("User name");
        input.setHeaderText(null);
        input.setContentText("Input your name:");
        input.showAndWait().ifPresent(this::addUser);
    }
    
}