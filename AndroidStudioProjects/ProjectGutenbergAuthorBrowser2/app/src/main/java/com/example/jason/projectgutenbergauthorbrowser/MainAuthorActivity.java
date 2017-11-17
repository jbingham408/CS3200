package com.example.jason.projectgutenbergauthorbrowser;

/*
    Jason Bingham
    CS3200 Assignment 5
    Project Gutenberg browser Custom View
 */

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainAuthorActivity extends ListActivity implements AdapterView.OnItemClickListener {

    //stores the listview of the activity
    ListView authListView;
    static final String LOG_KEY = MainAuthorActivity.class.getSimpleName() + "_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_author);

        //store author list
        String[] listOfAuthors;
        Resources res = getResources();
        authListView = getListView();

        //stores an array of the authors
        listOfAuthors = res.getStringArray(R.array.arrAuthors);
        ArrayList<Author> arrAuthors = new ArrayList<Author>();
        //split authors names
        for(String name : listOfAuthors){
            String[] firstLast = name.trim().split(" ");
            //checks if there are more than 2 strings in the array
            if(firstLast.length == 2) { // if there isn't a middle name
                arrAuthors.add(new Author(firstLast[0], firstLast[1]));
            }
            else{ // if there is a middle name
                arrAuthors.add(new Author(firstLast[0] + " " + firstLast[1], firstLast[2]));
            }
        }

        //create adapter
        AuthorAdapter adapter = new AuthorAdapter(this, R.layout.list_view, arrAuthors);
        authListView.setAdapter(adapter);
        authListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_author, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //creates an intent to start the next activity that will display the titles associated with the author
        Log.d("Author", authListView.getItemAtPosition(position).toString());
        Intent i = new Intent(getApplicationContext(), AuthorTitlesActivity.class);
        i.putExtra("author", authListView.getItemAtPosition(position).toString());
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_KEY, "onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_KEY, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_KEY, "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_KEY, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_KEY, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_KEY, "onDestroy()");
    }
}
