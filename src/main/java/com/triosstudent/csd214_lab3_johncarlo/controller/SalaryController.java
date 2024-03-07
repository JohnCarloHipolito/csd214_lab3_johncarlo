package com.triosstudent.csd214_lab3_johncarlo.controller;

import com.triosstudent.csd214_lab3_johncarlo.HRMgmtApplication;
import com.triosstudent.csd214_lab3_johncarlo.dao.SalaryDao;
import com.triosstudent.csd214_lab3_johncarlo.dao.UserDao;
import com.triosstudent.csd214_lab3_johncarlo.infrastructure.DBConnection;
import com.triosstudent.csd214_lab3_johncarlo.model.Salary;
import com.triosstudent.csd214_lab3_johncarlo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalaryController {

    @FXML
    private Label messageLbl;
    @FXML
    private Label userNameLbl;

    @FXML
    private TableView<Salary> salaryTbl;
    @FXML
    private TableColumn<Salary, Long> idCol;
    @FXML
    private TableColumn<Salary, String> nameCol;
    @FXML
    private TableColumn<Salary, LocalDate> payPeriodCol;
    @FXML
    private TableColumn<Salary, Double> amountCol;

    @FXML
    private TextField idTF;
    @FXML
    private ComboBox<String> userIdCB;
    @FXML
    private DatePicker payPeriodDP;
    @FXML
    private TextField amountTF;

    private String loggedInUser;
    private Map<Long, User> comBoxOptions;

    public static Double calcAnnualSalary(double monthlySalary) {
        if (monthlySalary < 0) {
            throw new IllegalArgumentException("Monthly salary cannot be negative.");
        }
        return monthlySalary * 12;
    }

    @FXML
    protected void initialize() throws SQLException {
        // set cell value factory for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        payPeriodCol.setCellValueFactory(new PropertyValueFactory<>("payPeriod"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // set the date picker to only allow the last day of the month
        payPeriodDP.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setDisable(true);
                        } else {
                            YearMonth yearMonthItem = YearMonth.from(item);
                            LocalDate lastDayOfMonth = yearMonthItem.atEndOfMonth();
                            setDisable(!item.equals(lastDayOfMonth));
                        }
                    }
                };
            }
        });

        // populate the user id combo box
        Connection connection = DBConnection.getInstance().getConnection();
        String readQuery = "SELECT * FROM tbl_user WHERE role = 'EMPLOYEE' ORDER BY id ASC";
        ResultSet resultSet = connection.createStatement().executeQuery(readQuery);
        comBoxOptions = new LinkedHashMap<>();
        while (resultSet.next()) {
            UserDao userDao = UserDao.mapResultSet(resultSet);
            User user = new User(userDao.getId(), userDao.getName(), userDao.getEmail(), userDao.getPhone());
            comBoxOptions.put(userDao.getId(), user);
        }
        userIdCB.getItems().add(null);
        comBoxOptions.forEach((key, value) -> userIdCB.getItems().add(key + " - " + value.getName()));

        salaryTbl.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Salary salary = salaryTbl.getSelectionModel().getSelectedItem();
                idTF.setText(String.valueOf(salary.getId()));
                comBoxOptions.entrySet().stream()
                        .filter(entry -> entry.getValue().getName().equals(salary.getName()))
                        .findFirst()
                        .ifPresent(entry -> userIdCB.setValue(entry.getKey() + " - " + entry.getValue().getName()));
                payPeriodDP.setValue(salary.getPayPeriod());
                amountTF.setText(String.valueOf(salary.getAmount()));
                messageLbl.setText("");
            }
        });

        //idTF should accept only numbers
        idTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^([1-9]\\d*)?$")) {
                idTF.setText(oldValue);
            }
        });

        //restrict only numbers and decimal
        amountTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^([0-9]+([,.][0-9]{0,2})?)?$")) {
                amountTF.setText(oldValue);
            }
        });

    }

    public void setLoggedInUser(String loggedInUser) {
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
    protected void onReadSalaryAction(ActionEvent event) throws SQLException {
        // read salaries from the database
        Connection connection = DBConnection.getInstance().getConnection();
        String readQuery = "SELECT * FROM tbl_salary, tbl_user WHERE tbl_salary.user_id = tbl_user.id";
        ResultSet resultSet = connection.createStatement().executeQuery(readQuery);
        // populate the table view with the salaries
        salaryTbl.getItems().clear();
        while (resultSet.next()) {
            SalaryDao salaryDao = SalaryDao.mapResultSet(resultSet);
            salaryTbl.getItems().add(new Salary(salaryDao.getId(), salaryDao.getUser().getName(),
                    salaryDao.getPayPeriod(), salaryDao.getAmount()));
        }
        // display a success message
        messageLbl.setTextFill(Color.GREEN);
        messageLbl.setText("Salaries were read successfully.");
        clearFields();
    }

    @FXML
    protected void onCreateSalaryAction(ActionEvent event) throws SQLException {
        //id should be null
        if (idTF.getText() != null && !idTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("ID should be empty.");
            return;
        }
        //user_id, pay_period, and amount should not be empty
        if (userIdCB.getValue().isEmpty() || payPeriodDP.getValue() == null || amountTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("User, Pay Period, and Amount should not be empty.");
            return;
        }
        // create a new salary
        Connection connection = DBConnection.getInstance().getConnection();
        String createQuery = "INSERT INTO tbl_salary (user_id, pay_period, amount) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(createQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, Long.parseLong(userIdCB.getValue().replaceAll(" - .+", "")));
        preparedStatement.setDate(2, java.sql.Date.valueOf(payPeriodDP.getValue()));
        preparedStatement.setDouble(3, Double.parseDouble(amountTF.getText()));
        preparedStatement.executeUpdate();

        // get the generated id
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            salaryTbl.getItems().add(new Salary(
                    generatedKeys.getLong(1),
                    userIdCB.getValue().replaceAll("^\\d+ - ", ""),
                    payPeriodDP.getValue(),
                    Double.parseDouble(amountTF.getText())));
            // display a success message
            messageLbl.setTextFill(Color.GREEN);
            messageLbl.setText("Salary created successfully.");
            clearFields();
        }

    }

    @FXML
    protected void onUpdateSalaryAction(ActionEvent event) throws SQLException {
        //id should not be null
        if (idTF.getText() == null || idTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("ID should not be empty. Double click on the table to select a salary.");
            return;
        }
        // id should exist in the table
        Long providedId = Long.parseLong(idTF.getText());
        boolean idExists = salaryTbl.getItems().stream().anyMatch(salary -> salary.getId().equals(providedId));
        if (!idExists) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("An existing 'Id' is required when updating a salary.");
            return;
        }
        // user_id, pay_period, and amount should not be empty
        if (userIdCB.getValue().isEmpty() || payPeriodDP.getValue() == null || amountTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("User, Pay Period, and Amount should not be empty.");
            return;
        }
        // update the salary
        Connection connection = DBConnection.getInstance().getConnection();
        String updateQuery = "UPDATE tbl_salary SET user_id=?, pay_period=?, amount=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setLong(4, Long.parseLong(idTF.getText()));
        preparedStatement.setLong(1, Long.parseLong(userIdCB.getValue().replaceAll(" - .*$", "")));
        preparedStatement.setDate(2, Date.valueOf(payPeriodDP.getValue()));
        preparedStatement.setDouble(3, Double.parseDouble(amountTF.getText()));
        preparedStatement.executeUpdate();
        // update the table view
        salaryTbl.getItems().stream()
                .filter(salary -> salary.getId() == Long.parseLong(idTF.getText()))
                .findFirst()
                .ifPresent(salary -> {
                    salary.setName(userIdCB.getValue().replaceAll("^\\d+ - ", ""));
                    salary.setPayPeriod(payPeriodDP.getValue());
                    salary.setAmount(Double.parseDouble(amountTF.getText()));
                    salaryTbl.refresh();
                    // display a success message
                    messageLbl.setTextFill(Color.GREEN);
                    messageLbl.setText("Salary updated successfully.");
                    clearFields();
                });
    }

    @FXML
    protected void onDeleteSalaryAction(ActionEvent event) throws SQLException {
        //id should not be null
        if (idTF.getText() == null || idTF.getText().isEmpty()) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("ID should not be empty. Double click on the table to select a salary.");
            return;
        }
        //id should exist in the table
        Long providedId = Long.parseLong(idTF.getText());
        boolean idExists = salaryTbl.getItems().stream().anyMatch(salary -> salary.getId().equals(providedId));
        if (!idExists) {
            messageLbl.setTextFill(Color.RED);
            messageLbl.setText("An existing 'Id' is required when deleting a salary.");
            return;
        }
        // delete the salary
        Connection connection = DBConnection.getInstance().getConnection();
        String deleteQuery = "DELETE FROM tbl_salary WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setLong(1, Long.parseLong(idTF.getText()));
        preparedStatement.executeUpdate();
        // remove the salary from the table view
        salaryTbl.getItems().removeIf(salary -> salary.getId() == Long.parseLong(idTF.getText()));
        // display a success message
        messageLbl.setTextFill(Color.GREEN);
        messageLbl.setText("Salary deleted successfully.");
        clearFields();
    }

    private void clearFields() {
        idTF.clear();
        userIdCB.setValue("");
        payPeriodDP.setValue(null);
        amountTF.clear();
    }
}
