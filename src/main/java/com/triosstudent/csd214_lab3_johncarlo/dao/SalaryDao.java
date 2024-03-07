package com.triosstudent.csd214_lab3_johncarlo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryDao {

    private Long id;
    private UserDao user;
    private LocalDate payPeriod;
    private Double amount;

    public static SalaryDao mapResultSet(ResultSet rs) throws SQLException {
        SalaryDao salaryDao = new SalaryDao();
        salaryDao.id = rs.getLong("tbl_salary.id");
        salaryDao.user = UserDao.mapResultSet(rs);
        salaryDao.payPeriod = rs.getDate("tbl_salary.pay_period").toLocalDate();
        salaryDao.amount = rs.getDouble("tbl_salary.amount");
        return salaryDao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDao getUser() {
        return user;
    }

    public void setUser(UserDao user) {
        this.user = user;
    }

    public LocalDate getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(LocalDate payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
