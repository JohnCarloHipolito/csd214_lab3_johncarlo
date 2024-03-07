package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import com.triosstudent.csd214_lab3_johncarlo.dao.UserDao;
import com.triosstudent.csd214_lab3_johncarlo.infrastructure.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private Label errorMessageLbl;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwordTF;

    @FXML
    protected void onLoginAction(ActionEvent event) throws SQLException, IOException {
        // validate email and password
        if (emailTF.getText().isEmpty() || passwordTF.getText().isEmpty()) {
            errorMessageLbl.setTextFill(Color.RED);
            errorMessageLbl.setText("Please provide email and password.");
            return;
        }
        // search for user in database with the provided email and password
        Connection connection = DBConnection.getInstance().getConnection();
        String readQuery = "SELECT * FROM tbl_user WHERE email=? AND password=? AND role='ADMIN'";
        PreparedStatement preparedStatement = connection.prepareStatement(readQuery);
        preparedStatement.setString(1, emailTF.getText());
        preparedStatement.setString(2, passwordTF.getText());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // load dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/dashboard-view.fxml"));
            Parent dashboard = fxmlLoader.load();
            DashboardController dashboardController = fxmlLoader.getController();
            // pass data to dashboard
            UserDao userDao = UserDao.mapResultSet(resultSet);
            dashboardController.setLoggedInUser(userDao.getName());
            // change scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(dashboard));
        } else {
            // show error message
            errorMessageLbl.setTextFill(Color.RED);
            errorMessageLbl.setText("Login failed! Please check your email and password.");
        }

    }
}