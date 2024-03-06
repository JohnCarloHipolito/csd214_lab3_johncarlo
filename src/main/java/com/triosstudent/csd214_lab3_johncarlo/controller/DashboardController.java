package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    private User loggedInUser;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void handleSalaryButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/salary-view.fxml"));
        Parent salary = fxmlLoader.load();
        SalaryController salaryController = fxmlLoader.getController();
        salaryController.setLoggedInUser(loggedInUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(salary));

    }

    public void handleEmployeeButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/employee-view.fxml"));
        Parent employee = fxmlLoader.load();
        EmployeeController employeeController = fxmlLoader.getController();
        employeeController.setLoggedInUser(loggedInUser);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(employee));


    }

    public void handleLogoutButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/login-view.fxml"));
        Parent login = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(login));

    }

    public void handleExitButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
