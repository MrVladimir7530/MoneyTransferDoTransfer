package org.example.model;

import java.math.BigDecimal;

public class PaymentsResult {
    private long id;
    private BigDecimal amount;
    private int status;
    private String account_from;
    private String account_to;
    private String description;

    public PaymentsResult() {

    }

    public PaymentsResult(long id, BigDecimal amount, int status, String account_from, String account_to) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.account_from = account_from;
        this.account_to = account_to;
    }

    public PaymentsResult(long id, BigDecimal amount, int status, String account_from, String account_to, String description) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.account_from = account_from;
        this.account_to = account_to;
        this.description = description;
    }
    public PaymentsResult(BigDecimal amount, int status, String account_from, String account_to, String description) {
        this.amount = amount;
        this.status = status;
        this.account_from = account_from;
        this.account_to = account_to;
        this.description = description;
    }

    public PaymentsResult(BigDecimal amount, int status, String account_from, String account_to) {
        this.amount = amount;
        this.status = status;
        this.account_from = account_from;
        this.account_to = account_to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccount_from() {
        return account_from;
    }

    public void setAccount_from(String account_from) {
        this.account_from = account_from;
    }

    public String getAccount_to() {
        return account_to;
    }

    public void setAccount_to(String account_to) {
        this.account_to = account_to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
