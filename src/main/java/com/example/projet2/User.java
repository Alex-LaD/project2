package com.example.projet2;

public class User {
    private int id;
    private String username;
    private String password;
    private String currency;

    public User(int id, String username, String password) {
        this(id,username,password,"USD");
    }

    public User(int id, String username, String password, String currency) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currency = currency;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCurrency() { return currency; }
}
