package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewAccount extends Activity implements View.OnClickListener {

    //stores the account name
    EditText account;
    Button submit;
    Button back;
    Button clear;

    BudgetDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        account = (EditText) findViewById(R.id.newAccountTxt);
        submit = (Button) findViewById(R.id.newAccountSubmitBtn);
        submit.setOnClickListener(this);
        back = (Button) findViewById(R.id.backNewAccountBtn);
        back.setOnClickListener(this);
        clear = (Button) findViewById(R.id.newAccountClearBtn);
        clear.setOnClickListener(this);

        db = new BudgetDB(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_account, menu);
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
            case R.id.newAccountSubmitBtn:
                //adds the account to the database
                if (!account.getText().toString().equals("")) {
                    db.addAccount(new Account(account.getText().toString()));
                    Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                break;
            case R.id.backNewAccountBtn:
                //closes the current activity
                finish();
                break;
            case R.id.newAccountClearBtn:
                //clears the edit text field
                account.setText("");
                break;
        }
    }
}
