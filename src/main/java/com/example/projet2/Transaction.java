package com.example.projet2;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private int userId;
    private double amount;
    private String category;
    private String description;
    private LocalDate date;

    public Transaction(int id, int userId, double amount, String category, String description, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
}
