package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeMainActivity extends Activity implements View.OnClickListener {

    //stores the buttons
    Button reportBtn;
    Button expenseBtn;
    Button categoryBtn;
    Button accountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        reportBtn = (Button) findViewById(R.id.viewReportBtn);
        reportBtn.setOnClickListener(this);
        expenseBtn = (Button) findViewById(R.id.expenseBtn);
        expenseBtn.setOnClickListener(this);
        categoryBtn = (Button) findViewById(R.id.viewCategoryBtn);
        categoryBtn.setOnClickListener(this);
        accountBtn = (Button) findViewById(R.id.viewAccountBtn);
        accountBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_main, menu);
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

        //checks which button is clicked
        switch(v.getId()){
            case R.id.viewCategoryBtn:
                //goes to the category activity
                Log.d("Button Press", "Category");
                i = new Intent(getApplicationContext(), BudgetCategory.class);
                startActivity(i);
                break;
            case R.id.expenseBtn:
                //goes to the expenses activity
                i = new Intent(getApplicationContext(), EnterExpense.class);
                startActivity(i);
                break;
            case R.id.viewAccountBtn:
                //goes to the account activity
                i = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(i);
                break;
            case R.id.viewReportBtn:
                //goes to the report activity
                i = new Intent(getApplicationContext(), BudgetReport.class);
                startActivity(i);
                break;
        }
    }
}
