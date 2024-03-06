module com.triosstudent.csd214_lab3_johncarlo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.triosstudent.csd214_lab3_johncarlo to javafx.fxml;
    exports com.triosstudent.csd214_lab3_johncarlo;
    exports com.triosstudent.csd214_lab3_johncarlo.controller;
    opens com.triosstudent.csd214_lab3_johncarlo.controller to javafx.fxml;
    exports com.triosstudent.csd214_lab3_johncarlo.model;
    opens com.triosstudent.csd214_lab3_johncarlo.model to javafx.fxml;
}