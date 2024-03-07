package com.triosstudent.csd214_lab3_johncarlo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;

    public UserDao() {
    }

    public static UserDao mapResultSet(ResultSet rs) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.id = rs.getLong("tbl_user.id");
        userDao.name = rs.getString("tbl_user.name");
        userDao.email = rs.getString("tbl_user.email");
        userDao.password = rs.getString("tbl_user.password");
        userDao.phone = rs.getString("tbl_user.phone");
        userDao.role = rs.getString("tbl_user.role");
        return userDao;
    }

    public UserDao(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
