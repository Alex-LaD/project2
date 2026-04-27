package com.example.projet2;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Transaction {

    private final IntegerProperty id       = new SimpleIntegerProperty();
    private final IntegerProperty userId   = new SimpleIntegerProperty();
    private final DoubleProperty  amount   = new SimpleDoubleProperty();
    private final StringProperty  category = new SimpleStringProperty();
    private final StringProperty  description = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    public Transaction(int id, int userId, double amount,
                       String category, String description, LocalDate date) {
        this.id.set(id);
        this.userId.set(userId);
        this.amount.set(amount);
        this.category.set(category);
        this.description.set(description);
        this.date.set(date);
    }

    public IntegerProperty idProperty()          { return id; }
    public IntegerProperty userIdProperty()      { return userId; }
    public DoubleProperty  amountProperty()      { return amount; }
    public StringProperty  categoryProperty()    { return category; }
    public StringProperty  descriptionProperty() { return description; }
    public ObjectProperty<LocalDate> dateProperty() { return date; }

    public int     getId()          { return id.get(); }
    public int     getUserId()      { return userId.get(); }
    public double  getAmount()      { return amount.get(); }
    public String  getCategory()    { return category.get(); }
    public String  getDescription() { return description.get(); }
    public LocalDate getDate()      { return date.get(); }

    public void setAmount(double v)      { amount.set(v); }
    public void setCategory(String v)    { category.set(v); }
    public void setDescription(String v) { description.set(v); }
    public void setDate(LocalDate v)     { date.set(v); }
}