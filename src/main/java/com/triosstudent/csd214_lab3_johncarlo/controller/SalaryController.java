package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SalaryController {

    public TextField idTF;
    private User loggedInUser;

    @FXML
    protected void initialize() {
        idTF.requestFocus();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void handleDashboardClick(MouseEvent mouseEvent) throws IOException {
       SceneRouter.routeToDashboard(mouseEvent, loggedInUser);
    }

    public void handleReadAction(ActionEvent event) {
    }

    public void handleCreateAction(ActionEvent event) {
    }

    public void handleUpdateAction(ActionEvent event) {
    }

    public void handleDeleteAction(ActionEvent event) {
    }
}
