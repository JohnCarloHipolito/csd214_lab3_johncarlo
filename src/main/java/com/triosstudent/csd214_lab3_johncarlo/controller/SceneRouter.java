package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneRouter {

    public static void routeToSalary(Event event, String loggedInUser) throws IOException {
        // Load the salary view
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/salary-view.fxml"));
        Parent salary = fxmlLoader.load();
        SalaryController salaryController = fxmlLoader.getController();
        // Pass the logged-in user to the salary controller
        salaryController.setLoggedInUser(loggedInUser);
        // Set the scene to the salary view
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(salary));
    }

    public static void routeToEmployee(Event event, String loggedInUser) throws IOException {
        // Load the employee view
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/employee-view.fxml"));
        Parent employee = fxmlLoader.load();
        EmployeeController employeeController = fxmlLoader.getController();
        // Pass the logged-in user to the employee controller
        employeeController.setLoggedInUser(loggedInUser);
        // Set the scene to the employee view
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(employee));
    }

    public static void routeToDashboard(Event event, String loggedInUser) throws IOException {
        // Load the dashboard view
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/dashboard-view.fxml"));
        Parent dashboard = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        // Pass the logged-in user to the dashboard controller
        dashboardController.setLoggedInUser(loggedInUser);
        // Set the scene to the dashboard view
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboard));
    }

    public static void routeToLogin(Event event) throws IOException {
        // Load the login view
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/login-view.fxml"));
        Parent login = fxmlLoader.load();
        // Set the scene to the login view
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(login));
    }

    public static void routToExit(Event event) {
        // Close the application
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
