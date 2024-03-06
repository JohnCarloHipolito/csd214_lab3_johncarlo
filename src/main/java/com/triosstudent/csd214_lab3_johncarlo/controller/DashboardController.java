package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;

import java.io.IOException;

public class DashboardController {

    private User loggedInUser;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void handleSalaryButtonAction(ActionEvent event) throws IOException {
       SceneRouter.routeToSalary(event, loggedInUser);
    }

    public void handleEmployeeButtonAction(ActionEvent event) throws IOException {
        SceneRouter.routeToEmployee(event, loggedInUser);
    }

    public void handleLogoutButtonAction(ActionEvent event) throws IOException {
        SceneRouter.routeToLogin(event);
    }

    public void handleExitButtonAction(ActionEvent event) {
        SceneRouter.routeToExit(event);
    }
}
