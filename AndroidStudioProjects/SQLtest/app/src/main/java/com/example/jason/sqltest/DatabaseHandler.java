package com.example.jason.sqltest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 11/21/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyTable";
    private static final String TABLE = "table";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMBER = "number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME
                + " TEXT, " + KEY_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addContact(Table table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, table.getName());
        values.put(KEY_NUMBER, table.getNumber());

        db.insert(TABLE, null, values);
        db.close();
    }

    public Table getTable(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] {KEY_ID, KEY_NAME, KEY_NUMBER}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Table table = new Table(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return table;
    }

    public List<Table> getAllTable(){
        List<Table> tableList = new ArrayList<Table>();
        String selectQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Table table = new Table();
                table.setID(Integer.parseInt(cursor.getString(0)));
                table.setName(cursor.getString(1));
                table.setNumber(cursor.getString(2));
                tableList.add(table);
            }while(cursor.moveToNext());
        }

        return tableList;
    }

    public int getTableCount(){
        String countQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
