package com.example.jason.projectgutenbergauthorbrowser;

/*
    Jason Bingham
    CS3200 Assignment 4
    Project Gutenberg browser
 */


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AuthorTitlesActivity extends ListActivity implements AdapterView.OnItemClickListener{

    //stores the listview
    ListView titlesList;
    //stores the array of titles for the selected author
    String authorTitles[];
    //stores the array of urls for the titles
    String authorTitlesURL[];
    static final String LOG_KEY = AuthorTitlesActivity.class.getSimpleName() + "_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_author_titles);

        //creates an intent to get the author that was selected in the main activity
        Intent i = getIntent();
        final String author = i.getStringExtra("author");

        //switch statement to fill the title array and url array with the correct information
        switch(author){
            case "Hans Christian Andersen":
                authorTitles = getResources().getStringArray(R.array.arrAndersenTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrAndersenTitleURL);
                break;
            case "Mark Twain":
                authorTitles = getResources().getStringArray(R.array.arrTwainTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrTwainTitleURL);
                break;
            case "Charles Dickens":
                authorTitles = getResources().getStringArray(R.array.arrDickenTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrDickenTitleURL);
                break;
            case "Rafael Sabatini":
                authorTitles = getResources().getStringArray(R.array.arrSabatiniTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrSabatiniTitleURL);
                break;
            case "Guy de Maupassant":
                authorTitles = getResources().getStringArray(R.array.arrMaupassantTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrMaupassantTitleURL);
                break;
            case "Victor Hugo":
                authorTitles = getResources().getStringArray(R.array.arrHugoTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrHugoTitleURL);
                break;
            case "Leo Tolstoy":
                authorTitles = getResources().getStringArray(R.array.arrTolstoyTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrTolstoyTitleURL);
                break;
            case "Rudyard Kipling":
                authorTitles = getResources().getStringArray(R.array.arrKiplingTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrKiplingTitleURL);
                break;
            case "Rabindranath Tagore":
                authorTitles = getResources().getStringArray(R.array.arrTagoreTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrTagoreTitleURL);
                break;
            case "William Shakespeare":
                authorTitles = getResources().getStringArray(R.array.arrShakespeareTitles);
                authorTitlesURL = getResources().getStringArray(R.array.arrShakespeareTitleURL);
                break;
        }

        //fills the listview with the titles for the selected author
        titlesList = this.getListView();
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, authorTitles);
        titlesList.setAdapter(adapt);
        titlesList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_author_titles, menu);
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
        //creates an intent to open a web browser
        Intent browserI;
        //the position determines which url to use
        switch(position){
            case 0:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[0]));
                startActivity(browserI);
                break;
            case 1:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[1]));
                startActivity(browserI);
                break;
            case 2:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[2]));
                startActivity(browserI);
                break;
            case 3:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[3]));
                startActivity(browserI);
                break;
            case 4:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[4]));
                startActivity(browserI);
                break;
            case 5:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[5]));
                startActivity(browserI);
                break;
            case 6:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[6]));
                startActivity(browserI);
                break;
            case 7:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[7]));
                startActivity(browserI);
                break;
            case 8:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[8]));
                startActivity(browserI);
                break;
            case 9:
                browserI = new Intent(Intent.ACTION_VIEW, Uri.parse(authorTitlesURL[9]));
                startActivity(browserI);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(AuthorTitlesActivity.LOG_KEY, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(AuthorTitlesActivity.LOG_KEY, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(AuthorTitlesActivity.LOG_KEY, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(AuthorTitlesActivity.LOG_KEY, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(AuthorTitlesActivity.LOG_KEY, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_KEY, "onDestroy()");
    }
}
