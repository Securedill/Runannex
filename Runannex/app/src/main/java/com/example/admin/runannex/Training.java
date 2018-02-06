package com.example.admin.runannex;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;



public class Training extends AppCompatActivity {
    SharedPreferences sPref;
    SharedPreferences.Editor ed;
    String weight,year,growth,name;
    ImageView imageView;
    int Seconds, Minutes, MilliSeconds ;
    Handler handler;
    Intent intent;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        ed = sPref.edit();
        weight = sPref.getString("weigh", "");
        name = sPref.getString("nam", "");
        growth = sPref.getString("growt", "");
        year = sPref.getString("yea", "");
        imageView = (ImageView) findViewById(R.id.image_view);
        String path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path + "/.Runannex/picture.png");
        final Button start = (Button) findViewById(R.id.start);
        final Button pause = (Button) findViewById(R.id.pause);
        final Button stop = (Button) findViewById(R.id.stop);
        final Button cont = (Button) findViewById(R.id.cont);
        final TextView timer = (TextView) findViewById(R.id.timer);

        handler = new Handler() ;




        /* if(f.exists() && !f.isDirectory()) {
            imageView.setImageURI(Uri.parse(new File("file://" + path + "/.Runannex/picture.png").toString()));
        } else { imageView.setImageResource(R.drawable.ava);} */













        View.OnClickListener oclBtnStart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
            }
        };
        start.setOnClickListener(oclBtnStart);


        View.OnClickListener oclBtnStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.putInt("Min", Minutes);
                ed.putInt("Millis", MilliSeconds);
                ed.putInt("Sec", Seconds);
                ed.commit();
                handler.removeCallbacks(runnable);
                intent = new Intent(v.getContext(), Result.class);
                startActivity(intent);
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                timer.setText("00:00:000");
                stop.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
            }
        };
        stop.setOnClickListener(oclBtnStop);


        View.OnClickListener oclBtnPause = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                pause.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.VISIBLE);
            }
        };
        pause.setOnClickListener(oclBtnPause);

        View.OnClickListener oclBtnCont = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                cont.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
            }
        };
        cont.setOnClickListener(oclBtnCont);
    }












    public Runnable runnable = new Runnable() {

        public void run() {
            final TextView timer = (TextView) findViewById(R.id.timer);
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            timer.setText(String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }

    };










    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}