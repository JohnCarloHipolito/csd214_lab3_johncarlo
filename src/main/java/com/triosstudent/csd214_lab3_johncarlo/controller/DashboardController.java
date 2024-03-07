package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    @FXML
    protected void onEmployeeButtonAction(ActionEvent event) throws IOException {
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

    @FXML
    protected void onLogoutButtonAction(ActionEvent event) throws IOException {
        // Load the login view
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/login-view.fxml"));
        Parent login = fxmlLoader.load();
        // Set the scene to the login view
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(login));
    }

    @FXML
    protected void onExitButtonAction(ActionEvent event) {
        // Close the application
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
