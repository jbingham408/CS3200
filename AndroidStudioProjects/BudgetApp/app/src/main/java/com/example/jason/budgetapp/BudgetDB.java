package com.example.jason.budgetapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BudgetDB extends SQLiteOpenHelper {

    //stores information about the database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budgetDB";

    //stores the table names
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_MONTH = "month";

    //stores the columns for the tables
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ACCOUNT = "account";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_EXP_TYPE = "type";
    private static final String COLUMN_MONTH = "month";

    public BudgetDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creates the expense table
        String createExpenseTable = "CREATE TABLE " + TABLE_EXPENSE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ACCOUNT_ID + " INTEGER, " + COLUMN_CATEGORY_ID
                + " INTEGER," + COLUMN_AMOUNT + " REAL, " + COLUMN_EXP_TYPE + " TEXT, " + COLUMN_NOTES + " TEXT, FOREIGN KEY(" + COLUMN_ACCOUNT_ID + ") REFERENCES "+ TABLE_ACCOUNT + " (" + COLUMN_ID + "), FOREIGN KEY("
                + COLUMN_CATEGORY_ID + ") REFERENCES "+ TABLE_CATEGORY + " (" + COLUMN_ID + "))";
        //creates the account table
        String createAccountTable = "CREATE TABLE " + TABLE_ACCOUNT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_ACCOUNT + " TEXT UNIQUE)";
        //creates the category table
        String createCategoryTable = "CREATE TABLE " + TABLE_CATEGORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_CATEGORY
                + " TEXT UNIQUE," + COLUMN_AMOUNT + " REAL)";
        //creates the month table
        String createMonthTable = "CREATE TABLE " + TABLE_MONTH + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_MONTH + " TEXT)";
        db.execSQL(createMonthTable);
        db.execSQL(createCategoryTable);
        db.execSQL(createAccountTable);
        db.execSQL(createExpenseTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTH);
        onCreate(db);
    }

    //adds an expense to the expense table
    public void addExpense(Expense expense){
        //gets the information for the category associated with the expense
        Category category = getCategoryByString(expense.getCategory());
        //gets the information for the account associated with the expense
        Account account = getAccountByString(expense.getAccount());
        //Log.d("category id", String.valueOf(category.getId()));
        //Log.d("account id", String.valueOf(account.getId()));

        //stores all the values of the expense to be entered into the table
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT_ID, account.getId());
        values.put(COLUMN_CATEGORY_ID, category.getId());
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_EXP_TYPE, expense.getType());
        values.put(COLUMN_NOTES, expense.getNotes());

        //inserts the new expense
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXPENSE, null, values);
        db.close();
    }

    //gets all the expense for a given category
    public List<Expense> getExpenses(String category){
        List<Expense> results = new ArrayList<Expense>();
        Category cate = getCategoryByString(category);
        //creates a query based on the category
        String query = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_CATEGORY_ID + " IS " + cate.getId();

        SQLiteDatabase db = this.getWritableDatabase();

        //gets the info from the database
        Cursor cursor = db.rawQuery(query, null);
        Expense expense;

        //puts the info into a expense list
        if (cursor.moveToFirst()) {
            do {
                expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                expense.setAccount(getAccountByID(Integer.parseInt(cursor.getString(1))));
                expense.setCategory(getCategoryByID(Integer.parseInt(cursor.getString(2))));
                expense.setAmount(Double.parseDouble(cursor.getString(3)));
                expense.setType(cursor.getString(4));
                expense.setNotes(cursor.getString(5));
                results.add(expense);
            } while (cursor.moveToNext());
        }
        return results;
    }

    //gets all expenses in the expense table
    public List<Expense> getAllExpenses(){

        List<Expense> expenseArray = new ArrayList<Expense>();
        String query = "SELECT * FROM " + TABLE_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Expense expense;

        //puts all expenses into a list
        if(cursor.moveToFirst()){
            do{
                expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                expense.setAccount(getAccountByID(Integer.parseInt(cursor.getString(1))));
                expense.setCategory(getCategoryByID(Integer.parseInt(cursor.getString(2))));
                expense.setAmount(Double.parseDouble(cursor.getString(3)));
                expense.setType(cursor.getString(4));
                expense.setNotes(cursor.getString(5));
                expenseArray.add(expense);
            }while(cursor.moveToNext());
        }
        else
            expense = null;

        db.close();
        return expenseArray;
    }

    //removes an expense based on the selected category
    public void removeExpensesCategory(String category){
        int categoryID = getCategoryByString(category).getId();
        String query = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_CATEGORY_ID + " IS " + categoryID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Expense expense;

        //removes all expenses with associated with the given category
        if(cursor.moveToFirst()){
            do{
                expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_EXPENSE, COLUMN_ID + " = " + expense.getId(), null);
            }while(cursor.moveToNext());
        }
    }

    //removes expenses based on the selected account
    public void removeExpensesAccount(String account){
        int accountID = getAccountByString(account).getId();
        String query = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_ACCOUNT_ID + " IS " + accountID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Expense expense;

        //removes all expenses associated with the account
        if(cursor.moveToFirst()){
            do{
                expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_EXPENSE, COLUMN_ID + " = " + expense.getId(), null);
            }while(cursor.moveToNext());
        }
    }

    //completely clears the expense table
    public void clearExpense(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSE);
    }

    //adds an account to the accounts table
    public void addAccount(Account account){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT, account.getAccount());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    //gets all accounts from the accounts table
    public List<Account> getAllAccounts(){

        List<Account> accountList = new ArrayList<Account>();

        String query = "SELECT * FROM " + TABLE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Account account;

        //puts the accounts into a list
        if(cursor.moveToFirst()){
            do {
                account = new Account();
                account.setId(Integer.parseInt(cursor.getString(0)));
                account.setAccount(cursor.getString(1));
                accountList.add(account);
            }while(cursor.moveToNext());
        }
        else
            account = null;

        db.close();
        return accountList;
    }

    //gets information about an account using the name
    public Account getAccountByString(String account) {
        Account accountResult = new Account();

        String query = "Select * FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_ACCOUNT + " IS '" + account + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //returns the requested account
        if(cursor.moveToFirst()){
            accountResult.setId(Integer.parseInt(cursor.getString(0)));
            accountResult.setAccount(cursor.getString(1));
        }

        db.close();
        return accountResult;
    }

    //gets information about an account using its id
    public String getAccountByID(int id) {
        Account accountResult = new Account();

        String query = "Select * FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_ID+ " IS " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            accountResult.setId(Integer.parseInt(cursor.getString(0)));
            accountResult.setAccount(cursor.getString(1));
        }

        db.close();
        return accountResult.getAccount();
    }

    //deletes an account from the accounts table
    public void deleteAccount(String account){
        String query = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_ACCOUNT + " IS '" + account + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Account oldAccount = new Account();

        if(cursor.moveToFirst()){
            oldAccount.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_ACCOUNT, COLUMN_ID + " = " + oldAccount.getId(), null);
        }
    }

    //adds the changes made to selected account
    public void updateAccount(String newAccount, String oldAccount){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT, newAccount);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_ACCOUNT, values, COLUMN_ACCOUNT + " = '" + oldAccount + "'", null);
    }

    //adds a new category to the category table
    public void addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category.getCategory());
        values.put(COLUMN_AMOUNT, category.getAmount());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    // returns all categories from the category table
    public List<Category> getAllCategories(){

        List<Category> categoryList = new ArrayList<Category>();

        String query = "SELECT * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Category category;

        //puts the categories into a list
        if(cursor.moveToFirst()){
            do {
                category = new Category();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setCategory(cursor.getString(1));
                category.setAmount(Double.parseDouble(cursor.getString(2)));
                categoryList.add(category);
            }while(cursor.moveToNext());
        }
        else
            category = null;

        db.close();
        return categoryList;
    }

    //gets information about a category using the categories name
    public Category getCategoryByString(String category) {
        Category categoryResult = new Category();

        String query = "Select * FROM " + TABLE_CATEGORY + " WHERE " + COLUMN_CATEGORY + " IS '" + category + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            categoryResult.setId(Integer.parseInt(cursor.getString(0)));
            categoryResult.setCategory(cursor.getString(1));
            categoryResult.setAmount(Double.parseDouble(cursor.getString(2)));
        }

        db.close();
        return categoryResult;
    }

    //gets information about a category using the categories id
    public String getCategoryByID(int id) {
        Category categoryResult = new Category();

        String query = "Select * FROM " + TABLE_CATEGORY + " WHERE " + COLUMN_ID + " IS " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            categoryResult.setId(Integer.parseInt(cursor.getString(0)));
            categoryResult.setCategory(cursor.getString(1));
            categoryResult.setAmount(Double.parseDouble(cursor.getString(2)));
        }

        db.close();
        return categoryResult.getCategory();
    }

    //deletes the category from the category table
    public void deleteCategory(String category){
        String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + COLUMN_CATEGORY + " IS '" + category + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Category oldCategory =  new Category();

        if(cursor.moveToFirst()){
            oldCategory.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_CATEGORY, COLUMN_ID + " = " + oldCategory.getId(), null);
        }
    }

    //updates any changes made to a category
    public void updateCategory(String category, double amount){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_AMOUNT, amount);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CATEGORY, values, COLUMN_CATEGORY + " = '" + category + "'", null);
    }

    //adds the month to the month table
    public void addMonth(String month){
        ContentValues value = new ContentValues();
        value.put(COLUMN_MONTH, month);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MONTH, null, value);
    }

    // changes the month stored in the table
    public void updateMonth(String month){
        ContentValues value = new ContentValues();
        value.put(COLUMN_MONTH, month);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_MONTH, value, COLUMN_MONTH + " = '" + month + "'", null);
    }

    // returns the month stored in the month table
    public String getMonth(){
        String month;
        String query = "SELECT * FROM " + TABLE_MONTH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            month = cursor.getString(1);
        }
        else{
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat monthDate = new SimpleDateFormat("MMMM");
            month = monthDate.format(calendar.getTime());
            addMonth(month);
        }

        return month;
    }
}
