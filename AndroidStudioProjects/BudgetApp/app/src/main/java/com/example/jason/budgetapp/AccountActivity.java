package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends Activity implements View.OnClickListener{

    Spinner accountList;
    Button editAccount;
    Button addAccount;
    Button goHome;

    //stores the database
    BudgetDB budgetDB;

    //stores the list of accounts
    List<Account> accounts;
    ArrayList<String> accountsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //initilize everything
        accountList = (Spinner) findViewById(R.id.accountDropDown);
        editAccount = (Button) findViewById(R.id.editAccountBtn);
        editAccount.setOnClickListener(this);
        addAccount = (Button) findViewById(R.id.addAccountBtn);
        addAccount.setOnClickListener(this);
        goHome = (Button) findViewById(R.id.accHomeBtn);
        goHome.setOnClickListener(this);

        budgetDB = new BudgetDB(this);

        accounts = new ArrayList<Account>();
        accountsArray = new ArrayList<String>();

        //adds all the accounts from the database to the spinner
        updateAccountList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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
        Intent i;
        switch (v.getId()){
            case R.id.addAccountBtn:
                //goes to the add new account activity
                i = new Intent(getApplicationContext(), NewAccount.class);
                startActivity(i);
                break;
            case R.id.editAccountBtn:
                //goes to edit selected account activity
                i = new Intent(getApplicationContext(), EditAccount.class);
                i.putExtra("account", accountList.getSelectedItem().toString());
                startActivity(i);
                break;
            case R.id.accHomeBtn:
                //closes the current activity
                finish();
                break;
        }

    }

    //updates the list of accounts in the database
    public void updateAccountList(){
        accounts = budgetDB.getAllAccounts();
        accountsArray.clear();
        for(Account acc : accounts){
            accountsArray.add(acc.getAccount());
        }

        //puts the list into the spinner
        ArrayAdapter<String> accountArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accountsArray);
        accountList.setAdapter(accountArrayAdapter);
    }
}
