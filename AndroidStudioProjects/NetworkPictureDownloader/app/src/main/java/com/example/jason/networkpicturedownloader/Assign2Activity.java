package com.example.jason.networkpicturedownloader;

/*
    Jason Bingham
    CS3200 Assignment 2
    Download images
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Assign2Activity extends Activity implements View.OnClickListener{

    // static strings for the imgae URLS, display messages, and file path
    static final String DOWNLOAD_MESSAGE = "Downloading image: ";
    static final String DOWNLOAD_COMPLETE = "Image Downloaded";
    static final String DYERS_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/dyers.jpg";
    static final String MOON_PINE_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/moonpine.jpg";
    static final String PLUM_ESTATE_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/plum.jpg";
    static final String USHIMACHI_URL = "http://www.ibiblio.org/wm/paint/auth/hiroshige/takanawa.jpg";
    static final String FILE_PATH = Environment.getExternalStorageDirectory().toString();

    // stores the selected image url and image
    String imageURL = "";
    String image = "";

    // stores the buttons for the app
    Button btnDyerDownload;
    Button btnMoonDownload;
    Button btnPlumDownload;
    Button btnUshimachiDownload;
    // stores the imageview for viewing the image
    ImageView imageView;
    // stores the progress dialog box
    ProgressDialog progressBarDialog;
    Handler progressBarHandler = new Handler();
    // stores how much has been downloaded
    int downloadStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign2_downloader);

        // creates the buttons
        btnDyerDownload = (Button) findViewById(R.id.btnDyers);
        btnDyerDownload.setOnClickListener(this);
        btnMoonDownload = (Button) findViewById(R.id.btnMoonPine);
        btnMoonDownload.setOnClickListener(this);
        btnPlumDownload = (Button) findViewById(R.id.btnPlumEstate);
        btnPlumDownload.setOnClickListener(this);
        btnUshimachiDownload = (Button) findViewById(R.id.btnUshimachi);
        btnUshimachiDownload.setOnClickListener(this);
        //creates the imageview
        imageView = (ImageView) findViewById(R.id.imageDownloadedView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assign2, menu);
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
    public void onClick(View v){

        downloadStatus = 0;

        // determines which button was pressed and sets the imageURL and image to the correct values
        switch(v.getId()){
            case R.id.btnDyers:
                imageURL = DYERS_URL;
                image = "dyers.jpg";
                break;
            case R.id.btnMoonPine:
                imageURL = MOON_PINE_URL;
                image = "moonpine.jpg";
                break;
            case R.id.btnPlumEstate:
                imageURL = PLUM_ESTATE_URL;
                image = "plum.jpg";
                break;
            case R.id.btnUshimachi:
                imageURL = USHIMACHI_URL;
                image = "takanawa.jpg";
        }

        // initializes the progess dialog box
        progressBarDialog = new ProgressDialog(v.getContext());
        progressBarDialog.setCancelable(true);
        progressBarDialog.setMessage(DOWNLOAD_MESSAGE + image);
        progressBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBarDialog.setProgress(0);
        progressBarDialog.setMax(100);
        progressBarDialog.show();

        // starts the download thread
        Thread thread = new Thread(){
            public void run(){
                fileDownload();
            }
        };
        thread.start();
    }

    public void fileDownload(){
        while(downloadStatus != 100){
            // stores the count of loops
            int i;

            // starts trying to download the image
            try{
                // sets up the URL of the wanted image
                URL url = new URL(imageURL);
                // connects to the url
                URLConnection connection = url.openConnection();
                connection.connect();

                // gets the the size of the file
                int fileLength = connection.getContentLength();

                // creates the input and output files for the image
                InputStream in = new BufferedInputStream(url.openStream(), 8192);
                OutputStream out = new FileOutputStream(FILE_PATH + "/" + image);

                byte data[] = new byte[1024];
                long total = 0;

                // loops till the image is downloaded
                while((i = in.read(data)) != -1) {
                    // keeps track of how much is downloaded
                    total += i;

                    // writes the image to the file
                    out.write(data, 0, i);
                    // updates how much has been downloaded
                    downloadStatus = (int) ((total * 100) / fileLength);
                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // updates the progress dialog box
                            progressBarDialog.setProgress(downloadStatus);
                            // if the image is done downloading, it is displayed
                            if (downloadStatus == 100) {
                                progressBarDialog.setMessage(DOWNLOAD_COMPLETE);
                                imageView.setImageDrawable(Drawable.createFromPath(FILE_PATH + "/" + image));
                            }
                        }
                    });
                }

                // closes the input and output
                out.flush();
                out.close();
                in.close();

            } catch(Exception e){
                Log.e("Failed", e.getMessage());
            }
        }

        // allows you to see the image has finished downloading
        if(downloadStatus >= 100){
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            // closes the progress dialog box
            progressBarDialog.dismiss();
        }
    }
}
