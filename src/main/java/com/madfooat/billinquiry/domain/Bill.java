package com.madfooat.billinquiry.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bill {

    private LocalDate dueDate;
    private BigDecimal dueAmount;
    private BigDecimal fees;


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(BigDecimal dueAmount) {
        this.dueAmount = dueAmount;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "dueDate=" + dueDate +
                ", dueAmount=" + dueAmount +
                ", fees=" + fees +
                '}';
    }
}
