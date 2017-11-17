package com.example.jason.pdftest;

import android.app.Activity;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends Activity implements View.OnClickListener {

    Button pdfButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfButton = (Button) findViewById(R.id.pdfBtn);
        pdfButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        File path = new File(Environment.getExternalStorageDirectory() + "/pdf/");
        if(!path.exists()){
            path.mkdir();
        }

        File file = new File(path, "test.pdf");
        try {
            FileOutputStream fout = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
