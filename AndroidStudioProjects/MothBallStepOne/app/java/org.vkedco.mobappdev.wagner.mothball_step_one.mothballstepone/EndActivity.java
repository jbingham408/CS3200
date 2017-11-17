package org.vkedco.mobappdev.wagner.mothball_step_one.mothballstepone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class EndActivity extends Activity {

    private TextView mYourScoreTV, mScoreTV, mYourBestTV, mBestTV;
    private Button mAgainButton, mMainMenuButton;
    private int WIDTH, HEIGHT, personalBest, points=0;

    private SharedPreferences mSharedPrefs=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        mYourScoreTV = (TextView) findViewById(R.id.your_score_tv);
        mScoreTV = (TextView) findViewById(R.id.score_tv);
        mYourBestTV = (TextView) findViewById(R.id.your_best_tv);
        mBestTV = (TextView) findViewById(R.id.best_tv);
        mAgainButton = (Button) findViewById(R.id.again_button);
        mMainMenuButton = (Button) findViewById(R.id.main_menu_button);

        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        HEIGHT = dMetrics.heightPixels;
        WIDTH = dMetrics.widthPixels;

        RelativeLayout.LayoutParams yourScoreLP = new RelativeLayout.LayoutParams((int)(WIDTH*0.5), (int)(HEIGHT*0.15));
        yourScoreLP.setMargins((int)(WIDTH*0.05), (int)(HEIGHT*0.04), 0, 0);
        mYourScoreTV.setLayoutParams(yourScoreLP);
        mYourScoreTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.035));

        RelativeLayout.LayoutParams scoreLP = new RelativeLayout.LayoutParams((int)(WIDTH*0.3), (int)(HEIGHT*0.15));
        scoreLP.setMargins((int)(WIDTH*0.55), (int)(HEIGHT*0.04), 0, (int)(WIDTH*0.18));
        mScoreTV.setLayoutParams(scoreLP);
        mScoreTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.035));

        RelativeLayout.LayoutParams yourBestLP = new RelativeLayout.LayoutParams((int)(WIDTH*0.5), (int)(HEIGHT*0.15));
        yourBestLP.setMargins((int)(WIDTH*0.05), (int)(HEIGHT*0.25), 0, 0);
        mYourBestTV.setLayoutParams(yourBestLP);
        mYourBestTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.035));

        RelativeLayout.LayoutParams bestLP = new RelativeLayout.LayoutParams((int)(WIDTH*0.3), (int)(HEIGHT*0.15));
        bestLP.setMargins((int)(WIDTH*0.55), (int)(HEIGHT*0.25), 0, (int)(WIDTH*0.18));
        mBestTV.setLayoutParams(bestLP);
        mBestTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.035));

        RelativeLayout.LayoutParams againButtonLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.5), (int)((float)HEIGHT*0.075));
        againButtonLP.setMargins((int)((float)WIDTH*0.26), (int)((float)HEIGHT*0.54), (int)((float)WIDTH*0.25), 0);
        mAgainButton.setLayoutParams(againButtonLP);
        mAgainButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        RelativeLayout.LayoutParams mainMenuButtonLP = new RelativeLayout.LayoutParams((int)((float)WIDTH*0.5), (int)((float)HEIGHT*0.075));
        mainMenuButtonLP.setMargins((int)((float)WIDTH*0.26), (int)((float)HEIGHT*0.69), (int)((float)WIDTH*0.25), 0);
        mMainMenuButton.setLayoutParams(mainMenuButtonLP);
        mMainMenuButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,(int)(HEIGHT*0.0125));

        if(getIntent().hasExtra(getResources().getString(R.string.player_score_key))){
            points=getIntent().getExtras().getInt(getResources().getString(R.string.player_score_key));
            mScoreTV.setText(Integer.toString(points)+"   ");
        }

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        personalBest = mSharedPrefs.getInt(getResources().getString(R.string.personal_best_key), 0);
        if(points>personalBest){
            SharedPreferences.Editor mSpEditor = mSharedPrefs.edit();
            mSpEditor.putInt(getResources().getString(R.string.personal_best_key), points);
            mSpEditor.commit();
            personalBest = points;
        }

        mBestTV.setText(Integer.toString(personalBest)+"   ");

        mAgainButton.setOnClickListener(
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

        mMainMenuButton.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                        try{
                            startActivity(i);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );

        //AdView adView = (AdView)this.findViewById(R.id.endAdView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        //adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
        try{
            startActivity(i);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
