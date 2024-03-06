package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EmployeeController {

    private User loggedInUser;

    @FXML
    protected void handleCrudOperation(ActionEvent event) {
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
