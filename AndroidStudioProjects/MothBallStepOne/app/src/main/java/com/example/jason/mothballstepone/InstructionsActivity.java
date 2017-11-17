package com.example.jason.mothballstepone;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InstructionsActivity extends Activity {

    private Button mBackButton = null;
    private TextView tv1=null, tv2=null, tv3=null, tv4=null, tv5=null;
    private ImageView mMothImage=null, mThreeStrikesImage=null;
    private int HEIGHT, WIDTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        mBackButton = (Button) findViewById(R.id.back_button);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        mMothImage = (ImageView) findViewById(R.id.imageView1);
        mThreeStrikesImage = (ImageView) findViewById(R.id.imageView2);

        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        HEIGHT = dMetrics.heightPixels;
        WIDTH = dMetrics.widthPixels;

        RelativeLayout.LayoutParams tv1LP = new RelativeLayout.LayoutParams(WIDTH, (int)((float)HEIGHT*0.12));
        tv1LP.setMargins(0, 0, 0, 0);
        tv1.setLayoutParams(tv1LP);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        RelativeLayout.LayoutParams tv2LP =new RelativeLayout.LayoutParams(WIDTH, (int)((float)HEIGHT*0.06));
        tv2LP.setMargins(0, (int)((float)HEIGHT*0.11), 0, 0);
        tv2.setLayoutParams(tv2LP);
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        RelativeLayout.LayoutParams tv3LP =new RelativeLayout.LayoutParams(WIDTH, (int)((float)HEIGHT*0.06));
        tv3LP.setMargins(0, (int)((float)HEIGHT*0.2), 0, 0);
        tv3.setLayoutParams(tv3LP);
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        RelativeLayout.LayoutParams mothImageLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.25), (int)(HEIGHT*0.125));
        mothImageLP.setMargins((int)((float)WIDTH*0.375), (int)(HEIGHT*0.28), (int)((float)WIDTH*0.375), 0);
        mMothImage.setLayoutParams(mothImageLP);

        RelativeLayout.LayoutParams tv4LP =new RelativeLayout.LayoutParams(WIDTH, (int)((float)HEIGHT*0.06));
        tv4LP.setMargins(0, (int)((float)HEIGHT*0.45), 0, 0);
        tv4.setLayoutParams(tv4LP);
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        RelativeLayout.LayoutParams tv5LP =new RelativeLayout.LayoutParams(WIDTH, (int)((float)HEIGHT*0.06));
        tv5LP.setMargins(0, (int)((float)HEIGHT*0.525), 0, 0);
        tv5.setLayoutParams(tv5LP);
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        RelativeLayout.LayoutParams threeStrikesImageLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.4), (int)(HEIGHT*0.1));
        threeStrikesImageLP.setMargins((int)((float)WIDTH*0.3), (int)(HEIGHT*0.585), (int)((float)WIDTH*0.3), 0);
        mThreeStrikesImage.setLayoutParams(threeStrikesImageLP);

        RelativeLayout.LayoutParams backButtonLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.5), (int)((float)HEIGHT*0.075));
        backButtonLP.setMargins((int)((float)WIDTH*0.25), (int)((float)HEIGHT*0.72), (int)((float)WIDTH*0.25), 0);
        mBackButton.setLayoutParams(backButtonLP);
        mBackButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.015));

        mBackButton.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        onBackPressed();
                    }
                }
        );

        //AdView adView = (AdView)this.findViewById(R.id.instructionsAdView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        //adView.loadAd(adRequest);
    }
}

