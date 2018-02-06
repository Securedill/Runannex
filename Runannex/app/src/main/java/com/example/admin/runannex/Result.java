package com.example.admin.runannex;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    SharedPreferences sPref;
    int Seconds, Minutes, MilliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView timer = (TextView) findViewById(R.id.timer);

        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        Seconds = sPref.getInt("Sec",0);
        Minutes = sPref.getInt("Min",0);
        MilliSeconds = sPref.getInt("Millis",0);
        timer.setText(String.format("%02d", Minutes) + ":"
                + String.format("%02d", Seconds) + ":"
                + String.format("%03d", MilliSeconds));


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
