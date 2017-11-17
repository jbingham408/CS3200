package com.example.jason.sqltest;

/**
 * Created by jason on 11/21/15.
 */
public class Table {
    int id;
    String name;
    String number;

    public Table(){}

    public Table(int id, String name, String number){
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Table(String name, String number){
        this.name = name;
        this.number = number;
    }

    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getNumber(){
        return this.number;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNumber(String number){
        this.number = number;
    }
}
