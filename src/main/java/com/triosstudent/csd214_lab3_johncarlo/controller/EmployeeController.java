package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

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

    private User loggedInUser;

    @FXML
    protected void initialize() {
        idTF.requestFocus();
    }

    @FXML
    protected void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        userNameLbl.setText(loggedInUser.getName());
    }

    @FXML
    protected void handleDashboardClick(MouseEvent mouseEvent) throws IOException {
        SceneRouter.routeToDashboard(mouseEvent, loggedInUser);
    }

    @FXML
    protected void handleReadAction(ActionEvent event) {
    }

    @FXML
    protected void handleCreateAction(ActionEvent event) {
    }

    @FXML
    protected void handleUpdateAction(ActionEvent event) {
    }

    @FXML
    protected void handleDeleteAction(ActionEvent event) {
    }
}
