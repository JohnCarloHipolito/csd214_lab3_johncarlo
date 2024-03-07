package com.triosstudent.csd214_lab3_johncarlo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML
    private Label dateLbl;
    @FXML
    private Label userLbl;

    private String loggedInUser;

    @FXML
    protected void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        userLbl.setText("Welcome, " + loggedInUser);
        dateLbl.setText("Today is " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
    }

    @FXML
    protected void onSalaryButtonAction(ActionEvent event) throws IOException {
        SceneRouter.routeToSalary(event, loggedInUser);
    }

    @FXML
    protected void onEmployeeButtonAction(ActionEvent event) throws IOException {
        SceneRouter.routeToEmployee(event, loggedInUser);
    }

    @FXML
    protected void onLogoutButtonAction(ActionEvent event) throws IOException {
       SceneRouter.routeToLogin(event);
    }

    @FXML
    protected void onExitButtonAction(ActionEvent event) {
        SceneRouter.routToExit(event);
    }
}
