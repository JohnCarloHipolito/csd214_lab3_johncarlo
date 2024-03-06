package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import com.triosstudent.csd214_lab3_johncarlo.infrastructure.DBConnection;
import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Label errorMessageLbl;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwordTF;

    @FXML
    protected void handleLoginAction(ActionEvent event) throws SQLException, IOException {
        // validate email and password
        if (emailTF.getText().isEmpty() || passwordTF.getText().isEmpty()) {
            errorMessageLbl.setTextFill(Color.RED);
            errorMessageLbl.setText("Please provide email and password.");
            return;
        }

        Connection connection = DBConnection.getInstance().getConnection();
        String readQuery = "SELECT * FROM tbl_user WHERE email=? AND password=? AND role='ADMIN'";
        PreparedStatement preparedStatement = connection.prepareStatement(readQuery);
        preparedStatement.setString(1, emailTF.getText());
        preparedStatement.setString(2, passwordTF.getText());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            User loggedInUser = new User();
            loggedInUser.setName(resultSet.getString("name"));
            loggedInUser.setEmail(resultSet.getString("email"));

            FXMLLoader fxmlLoader = new FXMLLoader(HRMgmtApplication.class.getResource("view/dashboard-view.fxml"));
            Parent dashboard = fxmlLoader.load();
            DashboardController dashboardController = fxmlLoader.getController();
            dashboardController.setLoggedInUser(loggedInUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(dashboard));
        } else {
            errorMessageLbl.setTextFill(Color.RED);
            errorMessageLbl.setText("Login failed.");
        }

    }
}