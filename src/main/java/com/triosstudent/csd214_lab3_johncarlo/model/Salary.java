package com.triosstudent.csd214_lab3_johncarlo.model;

public class Salary {

    private Long id;
    private Long userId;
    private String payPeriod;
    private Double amount;

    public Salary() {
    }

    public Salary(Long id, Long userId, String payPeriod, Double amount) {
        this.id = id;
        this.userId = userId;
        this.payPeriod = payPeriod;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
