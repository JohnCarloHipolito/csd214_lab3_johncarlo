module com.triosstudent.csd214_lab3_johncarlo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.triosstudent.csd214_lab3_johncarlo to javafx.fxml;
    exports com.triosstudent.csd214_lab3_johncarlo;
}