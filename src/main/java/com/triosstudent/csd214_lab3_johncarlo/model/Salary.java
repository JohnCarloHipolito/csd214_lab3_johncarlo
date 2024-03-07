package com.triosstudent.csd214_lab3_johncarlo.model;

import java.time.LocalDate;

public class Salary {

    private Long id;
    private String name;
    private LocalDate payPeriod;
    private Double amount;

    public Salary() {
    }

    public Salary(Long id, String name, LocalDate payPeriod, Double amount) {
        this.id = id;
        this.name = name;
        this.payPeriod = payPeriod;
        this.amount = amount;
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
