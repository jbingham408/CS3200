package com.example.jason.sqltest;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = getListView();
        DatabaseHandler db = new DatabaseHandler(this);

        db.addContact(new Table("Jason", "9703106415"));
        db.addContact(new Table("Jay", "9703106435"));

        List<Table> contacts = db.getAllTable();
        ArrayList<String> arrList = new ArrayList<String>();

        for(Table tb : contacts){
            String log = "ID: " + tb.getID() + ", Name: " + tb.getName() + ", Number: " + tb.getNumber();
            Log.d("Name: ", log);
            arrList.add(tb.getName() + tb.getNumber());
        }
        contactList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
