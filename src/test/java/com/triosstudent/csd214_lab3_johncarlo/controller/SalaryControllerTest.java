package com.triosstudent.csd214_lab3_johncarlo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SalaryControllerTest {

    @Test
    public void calcAnnualSalaryReturnsCorrectValueForPositiveInput() {
        Double expected = 60000.00;
        Double actual = SalaryController.calcAnnualSalary(5000.00);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void calcAnnualSalaryReturnsZeroForZeroInput() {
        Double expected = 0.0;
        Double actual = SalaryController.calcAnnualSalary(0.0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void calcAnnualSalaryThrowsExceptionForNegativeInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                SalaryController.calcAnnualSalary(-5000.00));
    }
}
