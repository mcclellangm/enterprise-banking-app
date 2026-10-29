package com.portfolio.banking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    private Long id;
    private String accountHolder;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public Long getId() {
        return id;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account(Long id, String accountHolder,double balance) {
        this.id = id;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    public Account() {
    }
}
