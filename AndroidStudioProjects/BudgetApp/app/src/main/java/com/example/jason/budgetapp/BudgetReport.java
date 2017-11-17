package com.example.jason.budgetapp;

import android.app.Activity;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BudgetReport extends Activity implements View.OnClickListener {

    //These the names of the columns used in the listview
    private static final String COL_ONE = "One";
    private static final String COL_TWO = "Two";
    private static final String COL_THREE = "Three";

    //stores the current month
    TextView month;
    //used to get the current month
    Calendar calendar;
    //stores the information from the database
    ListView listView;
    //stores the home button
    Button home;

    //stores the database
    BudgetDB db;

    //used to create a custom listview
    ReportListAdapter adapter;

    //stores the information from the database
    ArrayList<HashMap<String, String>> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_report);

        db = new BudgetDB(this);

        month = (TextView) findViewById(R.id.monthTxt);
        calendar = Calendar.getInstance();
        SimpleDateFormat monthDate = new SimpleDateFormat("MMMM");
        month.setText(monthDate.format(calendar.getTime()));

        listView = (ListView) findViewById(R.id.reportListView);
        fillList();
        adapter = new ReportListAdapter(this, reportList);
        listView.setAdapter(adapter);

        home = (Button) findViewById(R.id.reportGoHomeBtn);
        home.setOnClickListener(this);

        //checks if the month that is stored in the database is different than the current month
        if(!month.getText().toString().equals(db.getMonth())){
            //if the months are different, it saves a text file filled with the all the expenses from the database
            try {
                //saves the report and resets the expense table
                saveReport();
                db.updateMonth(month.getText().toString());
                db.clearExpense();
                reportList.clear();
                fillList();
                adapter = new ReportListAdapter(this, reportList);
                listView.setAdapter(adapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_report, menu);
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

    // fills the reportList with the information from the database
    public void fillList(){
        reportList = new ArrayList<HashMap<String, String>>();
        List<Category> categoryList = db.getAllCategories();
        List<Expense> expenseList = db.getAllExpenses();
        double total = 0.0;
        String category;
        String expCategory;
        String type;

        HashMap<String, String> tempMap;

        //loops through all the categories to get each category stored in the database
        for(Category cate : categoryList){
            category = cate.getCategory();
            //total stores the total amount of expenses
            total = 0.0;
            tempMap = new HashMap<String, String>();
            //puts category into the first column of the list view
            tempMap.put(COL_ONE, cate.getCategory());
            //puts the amount for the category into the second column
            tempMap.put(COL_TWO, "$ " + String.format("%.2f", cate.getAmount()));
            //loops through all the expenses in the expense table
            for(Expense expense : expenseList){
                type = expense.getType();
                expCategory = expense.getCategory();
                Log.d("Type Category", expense.getType() + " " + expense.getCategory() + " " + cate.getCategory());
                //checks if the type is an expense or income
                if(expCategory.equals(category)){
                    Log.d("Type", expense.getType());
                    //if expense it adds to total, if income it subtracts from total
                    if (type.equals("Expense")){
                        total += expense.getAmount();
                    }
                    else{
                        total -= expense.getAmount();
                    }
                }
            }
            //stores the total into the third column of the listview
            tempMap.put(COL_THREE, "$ " + String.format("%.2f", total));
            reportList.add(tempMap);
        }
    }

    //when the button is clicked it finishes the current activity
    @Override
    public void onClick(View v) {
        finish();
    }

    //saves the text file of the report on the phone storage
    public void saveReport() throws IOException {
        Log.d("Create File", "FILE");
        //stores the filepath to where the file is saved
        String filePath = Environment.getExternalStorageDirectory().toString() + "/Report"+ db.getMonth() + ".txt";
        //creates the file
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();

        OutputStream out = new FileOutputStream(file);

        //gets all the expenses in the database
        List<Expense> expenseList = db.getAllExpenses();

        //strings used to for formatting the file
        String header = "\t\t\t" + db.getMonth() + " Budget Report\n";
        String subHeader = String.format("%-20s", "Category")
                + String.format("%-20s", "Account")
                + String.format("%-12s", "Type")
                + String.format("%-10s", "Amount")
                + String.format("%-30s%n%n", "Notes");

        out.write(header.getBytes());
        out.write(subHeader.getBytes());

        //prints out each expense into the text file
        for(Expense expense : expenseList){
            String tempRow = String.format("%-20s", expense.getCategory())
                    + String.format("%-20s", expense.getAccount())
                    + String.format("%-12s", expense.getType())
                    + String.format("%-10.2f", expense.getAmount())
                    + String.format("%-30s%n", expense.getNotes());
            out.write(tempRow.getBytes());
        }

        out.close();
        //shows the toast when the file is saved
        Toast.makeText(this, db.getMonth() + " report has been saved to " + filePath, Toast.LENGTH_LONG).show();
    }
}
