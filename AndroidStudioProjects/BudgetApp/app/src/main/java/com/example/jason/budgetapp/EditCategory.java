package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditCategory extends Activity implements View.OnClickListener {

    //stores the label of the selected category
    TextView category;
    //stores the amount
    EditText amount;
    Button deleteCategory;
    Button saveChanges;
    Button back;

    BudgetDB budgetDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        //gets the intent
        Intent i = getIntent();
        //gets the category stored in the intent
        String passedCategory = i.getStringExtra("category");

        category = (TextView) findViewById(R.id.editCategoryTxt);
        //puts the category into the textview
        category.setText(passedCategory);
        amount = (EditText) findViewById(R.id.editAmountTxt);
        amount.setText(i.getStringExtra("amount"));
        deleteCategory = (Button) findViewById(R.id.deleteCategoryBtn);
        deleteCategory.setOnClickListener(this);
        saveChanges = (Button) findViewById(R.id.saveCategoryBtn);
        saveChanges.setOnClickListener(this);
        back = (Button) findViewById(R.id.backEditCategoryBtn);
        back.setOnClickListener(this);

        budgetDB = new BudgetDB(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_category, menu);
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
        switch(v.getId()){
            case R.id.saveCategoryBtn:
                //saves changes to database
                budgetDB.updateCategory(category.getText().toString(), Double.parseDouble(amount.getText().toString()));

                i = new Intent(getApplicationContext(), BudgetCategory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.deleteCategoryBtn:
                //deletes category from the database
                String delCategory = category.getText().toString();
                budgetDB.removeExpensesCategory(delCategory);
                budgetDB.deleteCategory(delCategory);

                i = new Intent(getApplicationContext(), BudgetCategory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.backEditCategoryBtn:
                //closes the current activity
                finish();
                break;
        }
    }
}
