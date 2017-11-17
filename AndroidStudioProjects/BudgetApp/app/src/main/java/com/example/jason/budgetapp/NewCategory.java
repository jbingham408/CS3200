package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCategory extends Activity implements View.OnClickListener{

    Button submit;
    Button back;
    Button clear;
    EditText newCategoryTxt;
    EditText newAmountTxt;
    BudgetDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        db = new BudgetDB(this);

        submit = (Button) findViewById(R.id.addCategoryBtn);
        submit.setOnClickListener(this);
        back = (Button) findViewById(R.id.backCategoryBtn);
        back.setOnClickListener(this);
        clear = (Button) findViewById(R.id.categoryClearBtn);
        clear.setOnClickListener(this);
        newCategoryTxt = (EditText) findViewById(R.id.newCateTxt);
        newAmountTxt = (EditText) findViewById(R.id.categoryAmountTxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_category, menu);
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
            case R.id.addCategoryBtn:
                //adds new category
                //checks if the text fields are empty
                if(!newCategoryTxt.getText().toString().equals("")) {
                    if(!newAmountTxt.getText().toString().equals("")) {
                        //adds to the database
                        db.addCategory(new Category(newCategoryTxt.getText().toString(), Double.parseDouble(newAmountTxt.getText().toString())));
                        Intent i = new Intent(getApplicationContext(), BudgetCategory.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }
                break;
            case R.id.categoryClearBtn:
                //clears the fields
                newCategoryTxt.setText("");
                newAmountTxt.setText("");
                break;
            case R.id.backCategoryBtn:
                //closes the activity
                finish();
                break;
        }
    }
}
