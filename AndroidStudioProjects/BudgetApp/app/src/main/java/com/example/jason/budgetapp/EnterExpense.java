package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EnterExpense extends Activity implements View.OnClickListener {

    //stores the menu of accounts
    Spinner accountMenu;
    //stores the menu of categories
    Spinner categoryMenu;
    //stores the menu of expense types
    Spinner expenseTypeMenu;
    //stores any notes about the expense
    EditText notes;
    //stores the amount of expense
    EditText amount;
    Button submit;
    Button clear;
    Button home;

    BudgetDB budgetDB;

    //stores the list of accounts and categories
    List<Category> categories;
    List<Account> accounts;
    ArrayList<String> categoriesStringArray;
    ArrayList<String> accountsStringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expense);

        Resources res = getResources();

        accountMenu = (Spinner) findViewById(R.id.accountDropDown);
        categoryMenu = (Spinner) findViewById(R.id.categoryDropDown);
        expenseTypeMenu = (Spinner) findViewById(R.id.expenseTypeList);
        notes = (EditText) findViewById(R.id.expenseNoteTxt);
        amount = (EditText) findViewById(R.id.expenseAmountTxt);
        submit = (Button) findViewById(R.id.expenseSubmitBtn);
        submit.setOnClickListener(this);
        clear = (Button) findViewById(R.id.newExpenseClearBtn);
        clear.setOnClickListener(this);
        home = (Button) findViewById(R.id.newExpenseHomeBtn);
        home.setOnClickListener(this);

        budgetDB = new BudgetDB(this);

        ArrayAdapter<String> accountsAdapter;
        ArrayAdapter<String> categoriesAdapter;
        ArrayAdapter<String> expTypeAdapter;

        categories = new ArrayList<Category>();
        accounts = new ArrayList<Account>();
        categoriesStringArray = new ArrayList<String>();
        accountsStringArray = new ArrayList<String>();

        String expTypeList[];
        expTypeList = res.getStringArray(R.array.expenseTypes);

        //gets list of categories and accounts
        getCategories();
        getAccounts();

        accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accountsStringArray);
        categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesStringArray);
        expTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, expTypeList);

        //fills the spinners with the list of accounts, categories, and expense type
        accountMenu.setAdapter(accountsAdapter);
        categoryMenu.setAdapter(categoriesAdapter);
        expenseTypeMenu.setAdapter(expTypeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.expenseSubmitBtn:
                //adds the new expense to the database
                //checks if there is an account in the database
                if (accountMenu.getSelectedItem() == null) {
                    //forces the user to add an account if there are none
                    Log.d("Accounts Problem", "No Accounts Selected");
                    Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(i);
                //checks if there is a category in the database
                } else if (categoryMenu.getSelectedItem() == null) {
                    //forces the user to add a category if there are none
                    Log.d("Category Problem", "No category selected");
                    Intent i = new Intent(getApplicationContext(), BudgetCategory.class);
                    startActivity(i);
                } else if (amount.getText().toString().equals("")) {
                    //there has to be an amount
                    Log.d("Amount Problem", "No amount entered");
                } else {
                    //adds all the info to the expense table

                    //Log.d("account", accountMenu.getSelectedItem().toString());
                    //Log.d("category", categoryMenu.getSelectedItem().toString());
                    String expAccount = accountMenu.getSelectedItem().toString();
                    String expCategory = categoryMenu.getSelectedItem().toString();
                    String expNotes = "";
                    String type = expenseTypeMenu.getSelectedItem().toString();
                    if (notes.getText() == null) {
                        expNotes = "";
                    } else {
                        expNotes = notes.getText().toString();
                    }
                    double expAmount = Double.parseDouble(amount.getText().toString());


                    Log.d("Category", expCategory);
                    Log.d("Account", expAccount);
                    //expense is added and clears the edit text fields
                    budgetDB.addExpense(new Expense(expAccount, expCategory, expAmount, expNotes, type));
                    amount.setText("");
                    notes.setText("");
                }
                break;
            case R.id.newExpenseClearBtn:
                //clears the edit text fields
                amount.setText("");
                notes.setText("");
                break;
            case R.id.newExpenseHomeBtn:
                //closes the current activity
                finish();
                break;
        }
    }

    //gets the categories from the database
    public void getCategories(){
        categories = budgetDB.getAllCategories();
        categoriesStringArray.clear();
        for(Category cate : categories){
            categoriesStringArray.add(cate.getCategory());
        }
    }

    //gets the accounts from the database
    public void getAccounts(){
        accounts = budgetDB.getAllAccounts();
        accountsStringArray.clear();
        for(Account acc : accounts){
            accountsStringArray.add(acc.getAccount());
        }
    }
}
