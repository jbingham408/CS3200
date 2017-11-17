package com.example.jason.budgetapp;

/**
 * Created by jason on 12/12/15.
 */
public class Expense {
    //stores the info about the expense
    private int id;
    private String account;
    private String category;
    private double amount;
    private String notes;
    private String type;

    public Expense(){}

    public Expense(String category){
        this.category = category;
        this.amount = 0.0;
    }

    public Expense(int id, String category){
        this.category = category;
        this.amount = 0.0;
    }

    public Expense(String account, String category, double amount){
        this.account = account;
        this.category = category;
        this.amount = amount;
        this.notes = "";
    }

    public Expense(String account, String category, double amount, String notes){
        this.account = account;
        this.category = category;
        this.amount = amount;
        this.notes = notes;
    }

    //initialize the expense info
    public Expense(String account, String category, double amount, String notes, String type){
        this.account = account;
        this.category = category;
        this.amount = amount;
        this.notes = notes;
        this.type = type;
    }

    public Expense(int id, String category, double amount){
        this.id = id;
        this.category = category;
        this.amount = amount;
    }

    //sets the id
    public void setId(int id){
        this.id = id;
    }

    //sets the category
    public void setCategory(String category){
        this.category = category;
    }

    //sets the amount
    public void setAmount(double amount){
        this.amount = amount;
    }

    //sets the account
    public void setAccount(String account){
        this.account = account;
    }

    //sets the note
    public void setNotes(String notes){
        this.notes = notes;
    }

    //sets the type
    public void setType(String type){
        this.type = type;
    }

    //returns the id
    public int getId(){
        return this.id;
    }

    //returns the category
    public String getCategory(){
        return this.category;
    }

    //returns the amount
    public double getAmount(){
        return this.amount;
    }

    //returns the notes
    public String getNotes(){
        return this.notes;
    }

    //returns the account
    public String getAccount(){
        return this.account;
    }

    //returns the type
    public String getType(){
        return this.type;
    }
}
