package com.example.jason.budgetapp;


public class Account {
    //stores the accounts id and name
    private int id;
    private String account;

    public Account(){}

    public Account(int id, String account){
        this.id = id;
        this.account = account;
    }

    //initalize the account info
    public Account(String account){
        this.account = account;
    }

    //sets the id
    public void setId(int id){
        this.id = id;
    }

    //sets the name
    public void setAccount(String account){
        this.account = account;
    }

    //returns the id
    public int getId(){
        return this.id;
    }

    //returns the name
    public String getAccount(){
        return this.account;
    }
}
