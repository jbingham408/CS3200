package com.example.jason.projectgutenbergauthorbrowser;

/*
    Jason Bingham
    CS3200 Assignment 5
    Project Gutenberg browser Custom View
 */

public class Author {
    private String firstName; // store first name
    private String lastName; // store last name

    public Author(){
        firstName = "";
        lastName = "";
    }

    public Author(String first, String last){
        firstName = first;
        lastName = last;
    }
     //get first name
    public String getFirstName(){
        return firstName;
    }
    //get last name
    public String getLastName(){
        return lastName;
    }

    public String toString(){
        return firstName + " " + lastName;
    }
}
