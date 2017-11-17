package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditAccount extends Activity implements View.OnClickListener {

    EditText accountName;
    Button saveBtn;
    Button deleteBtn;
    Button back;

    //store the databse
    BudgetDB budgetDB;

    //stores the name of the oldaccount
    String oldAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        //gets the intent to get the information stored in it.
        Intent i = getIntent();

        accountName = (EditText) findViewById(R.id.accountEditTxt);
        //sets the text to the account the was selected in previous activity
        accountName.setText(i.getStringExtra("account"));
        saveBtn = (Button) findViewById(R.id.saveAccountBtn);
        saveBtn.setOnClickListener(this);
        deleteBtn = (Button) findViewById(R.id.deleteAccountBtn);
        deleteBtn.setOnClickListener(this);
        back = (Button) findViewById(R.id.backEditAccountBtn);
        back.setOnClickListener(this);

        //stores the account in case it needs to get changed
        oldAccount = i.getStringExtra("account");

        budgetDB = new BudgetDB(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_account, menu);
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
            case R.id.deleteAccountBtn:
                //removes the account from the database
                budgetDB.removeExpensesAccount(accountName.getText().toString());
                budgetDB.deleteAccount(accountName.getText().toString());
                i = new Intent(getApplicationContext(), AccountActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.saveAccountBtn:
                //makes the changes to account
                budgetDB.updateAccount(accountName.getText().toString(), oldAccount);
                i = new Intent(getApplicationContext(), AccountActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.backEditAccountBtn:
                //closes the current activity
                finish();
                break;
        }
    }
}
