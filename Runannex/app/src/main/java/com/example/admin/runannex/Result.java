package com.example.admin.runannex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.File;
import java.io.OutputStream;

public class Result extends AppCompatActivity {

    SharedPreferences sPref;
    int Seconds, Minutes, MilliSeconds,caloriii,distance;
    float speed;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        final TextView timer = (TextView) findViewById(R.id.timer);
        final TextView speeder = (TextView) findViewById(R.id.halfV);
        final TextView caloriir = (TextView) findViewById(R.id.calorii);
        final TextView distancer = (TextView) findViewById(R.id.distance);

        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        Seconds = sPref.getInt("Sec",0);
        Minutes = sPref.getInt("Min",0);
        MilliSeconds = sPref.getInt("Millis",0);
        caloriii = sPref.getInt("cali",0);
        speed = sPref.getFloat("speed",0);
        distance = sPref.getInt("dist",0);
        timer.setText(String.format("%02d", Minutes) + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds));
        caloriir.setText(caloriii+"");
        speeder.setText((int)speed+"");
        distancer.setText(distance+"");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                    Toast.makeText(Result.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) { Toast.makeText(Result.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show(); }
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

}
