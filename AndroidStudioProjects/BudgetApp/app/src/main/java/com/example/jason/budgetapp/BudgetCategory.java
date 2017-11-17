package com.example.jason.budgetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetCategory extends Activity implements View.OnClickListener{

    //These the names of the columns used in the listview
    private static final String COL_ONE = "One";
    private static final String COL_TWO = "Two";
    private static final String COL_THREE = "Three";

    Spinner categoryList;
    Button editCategory;
    Button newCategory;
    Button homeScreen;
    ListView listView;
    TextView amountTxt;

    List<Category> categories;
    ArrayList<String> cateArrayList;

    //used to create a custom listview
    ReportListAdapter adapter;

    //stores a list of all the expenses for the selected category
    ArrayList<HashMap<String, String>> expenseList;

    //stores the database
    BudgetDB budgetdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_category);

        budgetdb = new BudgetDB(this);
        categoryList = (Spinner) findViewById(R.id.categoryList);
        //creates a onitemselectedlistener for the category spinner
        categoryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //when a item is selected it updates which expenses are displayed
                Log.d("Category", categoryList.getSelectedItem().toString());
                updateExpenses();
                getCategoryAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editCategory = (Button) findViewById(R.id.editCategoryBtn);
        editCategory.setOnClickListener(this);
        newCategory = (Button) findViewById(R.id.newCateBtn);
        newCategory.setOnClickListener(this);
        homeScreen = (Button) findViewById(R.id.homeBtn);
        homeScreen.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.cateExpensesList);
        amountTxt = (TextView) findViewById(R.id.cateAmount);

        ArrayAdapter<String> cateArrayAdapter;
        ArrayAdapter<String> expenseArrayAdapter;

        categories = new ArrayList<Category>();
        cateArrayList = new ArrayList<String>();

        updateCategories();

        //fill the category spinner with all the categories from the database
        cateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cateArrayList);
        categoryList.setAdapter(cateArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_category, menu);
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

        //intent to start a new activity based on which button is clicked
        Intent i;

        switch(v.getId()){
            case R.id.newCateBtn:
                //when the button to add a new category is clicked
                i = new Intent(getApplicationContext(), NewCategory.class);
                startActivity(i);
                updateCategories();
                break;
            case R.id.homeBtn:
                //ends the current activity when the goto home button is clicked
                finish();
                break;
            case R.id.editCategoryBtn:
                //edit the current selected category
                i = new Intent(getApplicationContext(), EditCategory.class);
                i.putExtra("category", categoryList.getSelectedItem().toString());
                i.putExtra("amount", amountTxt.getText().toString());
                startActivity(i);
                break;
        }

    }

    //updates the category list in the spinner
    public void updateCategories(){
        //gets all the categories from the database and puts them into the spinner
        categories = budgetdb.getAllCategories();
        cateArrayList.clear();
        for(Category cate : categories){
            cateArrayList.add(cate.getCategory());
        }

        ArrayAdapter<String> cateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cateArrayList);
        categoryList.setAdapter(cateArrayAdapter);
    }

    //updates the listview with all the expenses of the current selected category
    public void updateExpenses(){
        fillList();
        adapter = new ReportListAdapter(this, expenseList);
        listView.setAdapter(adapter);
    }

    //get the amount set for the current category
    public void getCategoryAmount(){
        Category tempCate = budgetdb.getCategoryByString(categoryList.getSelectedItem().toString());
        amountTxt.setText(String.format("%.2f", tempCate.getAmount()));
    }

    //fills the expense list with all the expenses for the selected category
    public void fillList(){
        expenseList = new ArrayList<HashMap<String, String>>();
        List<Expense> expObjectList = budgetdb.getExpenses(categoryList.getSelectedItem().toString());

        HashMap<String, String> tempMap;

        for(Expense expense : expObjectList){
            tempMap = new HashMap<String, String>();
            tempMap.put(COL_ONE, String.format("%.2f", expense.getAmount()));
            tempMap.put(COL_TWO, expense.getType());
            tempMap.put(COL_THREE, expense.getAccount());
            expenseList.add(tempMap);
        }

    }
}
