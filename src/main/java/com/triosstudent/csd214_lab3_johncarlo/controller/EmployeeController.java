package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import com.triosstudent.csd214_lab3_johncarlo.dao.UserDao;
import com.triosstudent.csd214_lab3_johncarlo.infrastructure.DBConnection;
import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class EmployeeController {

    @FXML
    private TableView<User> employeeTbl;
    @FXML
    private TableColumn<User, Long> idCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> phoneCol;

    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField phoneTF;

    @FXML
    private Label userNameLbl;
    @FXML
    private Label messageLbl;

    private String loggedInUser;

    @FXML
    protected void initialize() {
        // set cell value factory for each column
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("phone"));
        // copy the selected employee to the text fields when double-clicked
        employeeTbl.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                User user = employeeTbl.getSelectionModel().getSelectedItem();
                idTF.setText(String.valueOf(user.getId()));
                nameTF.setText(user.getName());
                emailTF.setText(user.getEmail());
                phoneTF.setText(user.getPhone());
                messageLbl.setText("");
            }
        });

        // set event handler for id text field to accept only numbers
        idTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^([1-9]\\d*)?$")) {
                idTF.setText(oldValue);
            }
        });
    }

    @FXML
    protected void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        userNameLbl.setText(loggedInUser);
    }

    @FXML
    protected void onDashboardHyperLinkClick(MouseEvent mouseEvent) throws IOException {
        SceneRouter.routeToDashboard(mouseEvent, loggedInUser);
    }


    @FXML
    protected void onLogoutHyperLinkClick(MouseEvent mouseEvent) throws IOException {
        SceneRouter.routeToLogin(mouseEvent);
    }

    @FXML
    protected void onExitHyperLinkClick(MouseEvent mouseEvent) {
        SceneRouter.routToExit(mouseEvent);
    }

    @FXML
    protected void onReadEmployeeAction(ActionEvent event) throws SQLException {
        // read all employees from the database
        Connection connection = DBConnection.getInstance().getConnection();
        String readQuery = "SELECT * FROM tbl_user WHERE role = 'EMPLOYEE'";
        ResultSet resultSet = connection.createStatement().executeQuery(readQuery);
        // populate the table view with the result set
        employeeTbl.getItems().clear();
        while (resultSet.next()) {
            UserDao userDao = UserDao.mapResultSet(resultSet);
            employeeTbl.getItems().add(new User(userDao.getId(), userDao.getName(), userDao.getEmail(), userDao.getPhone()));
        }
        // display a success message
        messageLbl.setTextFill(Color.GREEN);
        messageLbl.setText("Employees were read successfully.");

        clearFields();
    }

    @FXML
    protected void onCreateEmployeeAction(ActionEvent event) throws SQLException {
        // id should be null
        if (idTF.getText() != null && !idTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("ID should be empty. Click Read to clear the fields.");
            return;
        }
        // name, email, and phone should not be empty
        if (nameTF.getText().isEmpty() || emailTF.getText().isEmpty() || phoneTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("Name, Email, and Phone should not be empty.");
            return;
        }
        // insert the employee to the database
        Connection connection = DBConnection.getInstance().getConnection();
        String createQuery = "INSERT INTO tbl_user(name, email, phone, role) VALUES(?, ?, ?, 'EMPLOYEE')";
        PreparedStatement preparedStatement = connection.prepareStatement(createQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, nameTF.getText());
        preparedStatement.setString(2, emailTF.getText());
        preparedStatement.setString(3, phoneTF.getText());
        preparedStatement.executeUpdate();
        // get the generated id
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if(generatedKeys.next()) {
            // add the employee to the table view
            User user = new User();
            user.setId(generatedKeys.getLong(1));
            user.setName(nameTF.getText());
            user.setEmail(emailTF.getText());
            user.setPhone(phoneTF.getText());
            employeeTbl.getItems().add(user);
            messageLbl.setTextFill(Color.GREEN);
            messageLbl.setText("Employee created successfully.");
            clearFields();
        }
    }

    @FXML
    protected void onUpdateEmployeeAction(ActionEvent event) throws SQLException {
        //id should not be null
        if (idTF.getText() == null || idTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("ID should not be empty. Double click on the table to select an employee.");
            return;
        }
        // validate if id exists in the table
        Long providedId = Long.parseLong(idTF.getText());
        boolean idExists = employeeTbl.getItems().stream().anyMatch(user -> user.getId().equals(providedId));
        if (!idExists) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("An existing 'Id' is required when updating an employee.");
            return;
        }
        //name, email, and phone should not be empty
        if (nameTF.getText().isEmpty() || emailTF.getText().isEmpty() || phoneTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("Name, Email, and Phone should not be empty.");
            return;
        }
        // update the employee in the database
        Connection connection = DBConnection.getInstance().getConnection();
        String updateQuery = "UPDATE tbl_user SET name = ?, email = ?, phone = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setLong(4, Long.parseLong(idTF.getText()));
        preparedStatement.setString(1, nameTF.getText());
        preparedStatement.setString(2, emailTF.getText());
        preparedStatement.setString(3, phoneTF.getText());
        preparedStatement.executeUpdate();
        // update the employee in the table view
        employeeTbl.getItems().stream()
                .filter(user -> user.getId() == Long.parseLong(idTF.getText()))
                .findFirst()
                .ifPresent(user -> {
                    user.setName(nameTF.getText());
                    user.setEmail(emailTF.getText());
                    user.setPhone(phoneTF.getText());
                    employeeTbl.refresh();
                    messageLbl.setTextFill(Color.GREEN);
                    messageLbl.setText("Employee updated successfully.");
                    clearFields();
                });
    }

    @FXML
    protected void onDeleteEmployeeAction(ActionEvent event) throws SQLException {
        //id should not be null
        if (idTF.getText() == null || idTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("ID should not be empty. Double click on the table to select an employee.");
            return;
        }
        // validate if id exists in the table
        Long providedId = Long.parseLong(idTF.getText());
        boolean idExists = employeeTbl.getItems().stream().anyMatch(user -> user.getId().equals(providedId));
        if (!idExists) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("An existing 'Id' is required when deleting an employee.");
            return;
        }
        // delete the employee from the database
        Connection connection = DBConnection.getInstance().getConnection();
        String deleteQuery = "DELETE FROM tbl_user WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setLong(1, Long.parseLong(idTF.getText()));
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("Employee has a salary record. Delete the salary record first.");
            return;
        }
        // delete the employee from the table view
        employeeTbl.getItems().removeIf(user -> user.getId() == Long.parseLong(idTF.getText()));
        messageLbl.setTextFill(Color.GREEN);
        messageLbl.setText("Employee deleted successfully.");
        clearFields();
    }

    private void clearFields() {
        idTF.clear();
        nameTF.clear();
        emailTF.clear();
        phoneTF.clear();
    }

}
