package com.example.jason.budgetapp;

/**
 * Created by jason on 12/12/15.
 */
public class Category {
    //stores the id, name, and amount
    private int id;
    private String category;
    private double amount;

    public Category(){}

    public Category(int id, String category){
        this.id = id;
        this.category = category;
        this.amount = 0.0;
    }

    public Category(int id, String category, double amount){
        this.id = id;
        this.category = category;
        this.amount = amount;
    }

    //initalize the category info
    public Category(String category, double amount){
        this.category = category;
        this.amount = amount;
    }

    public Category(String category){
        this.category = category;
        this.amount = 0.0;
    }

    //set the id
    public void setId(int id){
        this.id = id;
    }

    //set the name
    public void setCategory(String category){
        this.category = category;
    }

    //set the amount
    public void setAmount(double amount){
        this.amount = amount;
    }

    //returns the id
    public int getId(){
        return this.id;
    }

    //returns the name
    public String getCategory(){
        return this.category;
    }

    //returns the amount
    public double getAmount(){
        return this.amount;
    }
}
