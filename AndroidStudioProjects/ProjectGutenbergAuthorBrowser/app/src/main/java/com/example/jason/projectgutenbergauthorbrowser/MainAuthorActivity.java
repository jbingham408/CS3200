package com.example.jason.projectgutenbergauthorbrowser;

/*
    Jason Bingham
    CS3200 Assignment 4
    Project Gutenberg browser
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainAuthorActivity extends ListActivity implements AdapterView.OnItemClickListener {

    //stores the listview
    ListView authorList;
    static final String LOG_KEY = MainAuthorActivity.class.getSimpleName() + "_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_author);

        //stores an array of the authors
        String author[] = getResources().getStringArray(R.array.arrAuthors);

        //displays the list of authors
        authorList = this.getListView();
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, author);
        authorList.setAdapter(adapt);
        authorList.setOnItemClickListener(this);
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
        Log.d("Author", authorList.getItemAtPosition(position).toString());
        Intent i = new Intent(getApplicationContext(), AuthorTitlesActivity.class);
        i.putExtra("author", authorList.getItemAtPosition(position).toString());
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
