package com.example.jason.mothballstepone;


/**
 * Created by Vladimir Kulyukin on 10/28/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;

public class MainMenuActivity extends Activity {

    private Button mInstructionsBtn = null;
    private Button mStartBtn = null;
    private ImageView mImageView = null;
    protected int WIDTH, HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mStartBtn = (Button) findViewById(R.id.start_button);
        mInstructionsBtn = (Button) findViewById(R.id.instructions_button);
        mImageView = (ImageView) findViewById(R.id.main_title_image);

        // DisplayMetrics is a class describing general information about a display, such as its size, density,
        // and font scaling.
        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        HEIGHT = dMetrics.heightPixels;
        WIDTH = dMetrics.widthPixels;

        // Customize the parameters for the start button
        RelativeLayout.LayoutParams startButtonLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.5), (int)((float)HEIGHT*0.075));
        startButtonLP.setMargins((int)((float)WIDTH*0.25), (int)((float)HEIGHT*0.62), (int)((float)WIDTH*0.25), (int)((float)HEIGHT*0.13));
        mStartBtn.setLayoutParams(startButtonLP);
        mStartBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        // Customize the parameters for the instruct button
        RelativeLayout.LayoutParams instructButtonLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.5), (int)((float)HEIGHT*0.075));
        instructButtonLP.setMargins((int)((float)WIDTH*0.25), (int)((float)HEIGHT*0.75), (int)((float)WIDTH*0.25), 0);
        mInstructionsBtn.setLayoutParams(instructButtonLP);
        mInstructionsBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        // Customize the parameters for the title image view
        RelativeLayout.LayoutParams titleImageViewLP = new RelativeLayout.LayoutParams(WIDTH, (int)(HEIGHT*0.4));
        titleImageViewLP.setMargins(0, (int)(HEIGHT*0.09), 0, 0);
        mImageView.setLayoutParams(titleImageViewLP);

        // start a GameActivity
        mStartBtn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent i = new Intent(getApplicationContext(), GameActivity.class);
                        try{
                            startActivity(i);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        // start an InstructionsActivity
        mInstructionsBtn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent i = new Intent(getApplicationContext(), InstructionsActivity.class);
                        try{
                            startActivity(i);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public void onBackPressed(){
        Intent backOut = new Intent(Intent.ACTION_MAIN); //implicit intent to return to home screen
        backOut.addCategory(Intent.CATEGORY_HOME);
        backOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backOut);
    }

}
