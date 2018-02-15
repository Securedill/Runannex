package com.example.admin.runannex;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.File;


public class Training extends AppCompatActivity implements OnMapReadyCallback {
    SharedPreferences sPref;
    SharedPreferences.Editor ed;
    String weight, year, growth, name;
    int Seconds, Minutes, MilliSeconds;
    Handler handler;
    Intent intent, intent2;
    GoogleMap map;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    ImageButton music;
    private final int SPORT_LIST = 1;
    private final int EXIT = 2;
    Boolean ifsport = true;
    boolean ifmaps = false;

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
        String path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path + "/.Runannex/picture.png");
        final Button start = (Button) findViewById(R.id.start);
        final Button pause = (Button) findViewById(R.id.pause);
        final Button stop = (Button) findViewById(R.id.stop);
        final Button cont = (Button) findViewById(R.id.cont);
        final TextView timer = (TextView) findViewById(R.id.timer);
        final Button ButtonMap = (Button) findViewById(R.id.Bmap);
        final ImageButton sport = (ImageButton) findViewById(R.id.sport);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView  calorii = (TextView) findViewById(R.id.calorii);
        final TextView  distance = (TextView) findViewById(R.id.distance);
        final TextView halfV = (TextView) findViewById(R.id.halfV);
        final TextView halfVr = (TextView) findViewById(R.id.halfVr);
        final TextView distancer = (TextView) findViewById(R.id.distancer);
        final TextView caloriir = (TextView) findViewById(R.id.caloriir);
        final ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();


        sport.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                showDialog(SPORT_LIST);


            }
        });


        handler = new Handler();
        mapFragment.getMapAsync(this);
        View.OnClickListener oclBtnMap = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonMap.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                sport.setVisibility(View.INVISIBLE);
                time.setVisibility(View.INVISIBLE);
                caloriir.setVisibility(View.INVISIBLE);
                halfV.setVisibility(View.INVISIBLE);
                halfVr.setVisibility(View.INVISIBLE);
                distancer.setVisibility(View.INVISIBLE);
                calorii.setVisibility(View.INVISIBLE);
                distance.setVisibility(View.INVISIBLE);
                music.setVisibility(View.INVISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                params.width = 1080;
                params.height = 1920;
                v.setLayoutParams(params);
                ifmaps = true;



            }
        };
        ButtonMap.setOnClickListener(oclBtnMap);

        music = (ImageButton) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >=15){
                    Intent music = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,Intent.CATEGORY_APP_MUSIC);
                    music.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(music);

                }
                else {
                    Intent music = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);

                    startActivity(music);
                }
            }
        });

        /* if(f.exists() && !f.isDirectory()) {
            imageView.setImageURI(Uri.parse(new File("file://" + path + "/.Runannex/picture.png").toString()));
        } else { imageView.setImageResource(R.drawable.ava);}  */


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
                intent = new Intent(v.getContext(), Result.class);
                startActivity(intent);
                ed.putInt("Min", Minutes);
                ed.putInt("Millis", MilliSeconds);
                ed.putInt("Sec", Seconds);
                ed.commit();
                handler.removeCallbacks(runnable);
                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSeconds = 0;
                stop.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                timer.setText(String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds) + ":"
                        + String.format("%03d", MilliSeconds));
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


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onMapReady(final GoogleMap googleMap) {
        final Button ButtonMap = (Button) findViewById(R.id.Bmap);
        map = googleMap;
        map.getUiSettings().setAllGesturesEnabled(false);

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings :

                return true;
            case R.id.action_problem:
                Intent i = new Intent(Intent.ACTION_SEND); i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[] {"slavafeatzhdos@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Ошибки");
                i.putExtra(Intent.EXTRA_TEXT,  "" );
                try { startActivity(Intent.createChooser(i, "Выбирите почту..."));
                    Toast.makeText(Training.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) { Toast.makeText(Training.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show(); }
                return true;
            case R.id.info:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("О приложении");
                builder.setMessage("Lorem ipsum dolor ....");
                builder.setPositiveButton("OK", null);
                builder.setIcon(R.drawable.ic_launcher);

                builder.show();
                return true;


        }
        return super.onOptionsItemSelected(item);

    }



    @Override
   protected Dialog onCreateDialog(final int id) {
        final ImageButton sport = (ImageButton) findViewById(R.id.sport);
        {
            switch (id) {
                case SPORT_LIST:
                    final String[] Sport = {"Бег", "Велосипед"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Выберите вид спорта");
                    builder.setItems(Sport, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int item) {
                            if (item == 0) {
                                sport.setImageResource(R.drawable.sport_running);
                            }
                            if (item == 1) {
                                sport.setImageResource(R.drawable.sport_cycling);
                            }

                            Toast.makeText(getApplicationContext(), "Выбранный спорт " + Sport[item], Toast.LENGTH_SHORT).show();

                        }

                    });
                    builder.setCancelable(true);
                    return builder.create();

                    case  EXIT:

                        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                                Training.this);
                        quitDialog.setTitle("Вы уверены что хотите выйти?");

                        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        quitDialog.show();

                    default:
                    return null;

            }
        }
    }

    public boolean onSupportNavigateUp(){
                 final Button start = (Button) findViewById(R.id.start);
                final Button pause = (Button) findViewById(R.id.pause);
                final Button stop = (Button) findViewById(R.id.stop);
                final Button cont = (Button) findViewById(R.id.cont);
                final TextView timer = (TextView) findViewById(R.id.timer);
                final Button ButtonMap = (Button) findViewById(R.id.Bmap);
                final ImageButton sport = (ImageButton) findViewById(R.id.sport);
                final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                final TextView time = (TextView) findViewById(R.id.time);
                final TextView  calorii = (TextView) findViewById(R.id.calorii);
                final TextView  distance = (TextView) findViewById(R.id.distance);
                final TextView halfV = (TextView) findViewById(R.id.halfV);
                final TextView halfVr = (TextView) findViewById(R.id.halfVr);
                final TextView distancer = (TextView) findViewById(R.id.distancer);
                final TextView caloriir = (TextView) findViewById(R.id.caloriir);
                final ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                ButtonMap.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                cont.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                sport.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
                caloriir.setVisibility(View.VISIBLE);
                halfV.setVisibility(View.VISIBLE);
                halfVr.setVisibility(View.VISIBLE);
                distancer.setVisibility(View.VISIBLE);
                calorii.setVisibility(View.VISIBLE);
                distance.setVisibility(View.VISIBLE);
                music.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                params.width =1080;
                params.height = 900;
                View v = mapFragment.getView();
                v.setLayoutParams(params);
                ifmaps = false;

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
           if (ifmaps) {



               final Button start = (Button) findViewById(R.id.start);
               final Button pause = (Button) findViewById(R.id.pause);
               final Button stop = (Button) findViewById(R.id.stop);
               final Button cont = (Button) findViewById(R.id.cont);
               final TextView timer = (TextView) findViewById(R.id.timer);
               final Button ButtonMap = (Button) findViewById(R.id.Bmap);
               final ImageButton sport = (ImageButton) findViewById(R.id.sport);
               final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
               final TextView time = (TextView) findViewById(R.id.time);
               final TextView  calorii = (TextView) findViewById(R.id.calorii);
               final TextView  distance = (TextView) findViewById(R.id.distance);
               final TextView halfV = (TextView) findViewById(R.id.halfV);
               final TextView halfVr = (TextView) findViewById(R.id.halfVr);
               final TextView distancer = (TextView) findViewById(R.id.distancer);
               final TextView caloriir = (TextView) findViewById(R.id.caloriir);
               final ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
               ButtonMap.setVisibility(View.VISIBLE);
               start.setVisibility(View.VISIBLE);
               pause.setVisibility(View.VISIBLE);
               stop.setVisibility(View.VISIBLE);
               cont.setVisibility(View.VISIBLE);
               timer.setVisibility(View.VISIBLE);
               sport.setVisibility(View.VISIBLE);
               time.setVisibility(View.VISIBLE);
               caloriir.setVisibility(View.VISIBLE);
               halfV.setVisibility(View.VISIBLE);
               halfVr.setVisibility(View.VISIBLE);
               distancer.setVisibility(View.VISIBLE);
               calorii.setVisibility(View.VISIBLE);
               distance.setVisibility(View.VISIBLE);
               music.setVisibility(View.VISIBLE);
               getSupportActionBar().setDisplayHomeAsUpEnabled(false);
               params.width =1080;
               params.height = 900;
               View v = mapFragment.getView();
               v.setLayoutParams(params);
               ifmaps = false;

               



           } else {

               showDialog(EXIT);

           }
        }
        return true;
    }



}