package com.example.jason.mathbooksonamazon;

/*
    Jason Bingham A01944907
    CS3200 Exam 1
    The XML arrays were copied from the problem pdf
 */


import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MathBookList extends ListActivity implements AdapterView.OnItemClickListener{

    // store the list view
    ListView isbnListView;
    // store the array of isbns
    private String[] isbns;
    // store the array of urls
    private String[] urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_math_book_list);

        Resources res = getResources();
        // get the isbns array
        isbns = res.getStringArray(R.array.isbns);
        // get the urls array
        urls = res.getStringArray(R.array.urls);
        // get the list view
        isbnListView = this.getListView();
        // create the array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, isbns);
        // set the list views adapter
        isbnListView.setAdapter(adapter);
        isbnListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_math_book_list, menu);
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

        // Intent for opening up the web browser
        Intent browsI;

        // switch statement to determine which isbn was clicked and open up the url that corresponds to it.
        switch(position){
            case 0:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[0]));
                startActivity(browsI);
                break;
            case 1:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[1]));
                startActivity(browsI);
                break;
            case 2:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[2]));
                startActivity(browsI);
                break;
            case 3:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[3]));
                startActivity(browsI);
                break;
            case 4:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[4]));
                startActivity(browsI);
                break;
            case 5:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[5]));
                startActivity(browsI);
                break;
            case 6:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[6]));
                startActivity(browsI);
                break;
            case 7:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[7]));
                startActivity(browsI);
                break;
            case 8:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[8]));
                startActivity(browsI);
                break;
            case 9:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[9]));
                startActivity(browsI);
                break;
            case 10:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[10]));
                startActivity(browsI);
                break;
            case 11:
                browsI = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[11]));
                startActivity(browsI);
                break;
        }
    }
}
