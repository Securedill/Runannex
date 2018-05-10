package com.example.admin.runannex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Alltrain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Phone1> alltrainlay = new ArrayList<>();
    ImageView imageMap;
    SharedPreferences sPref;
    SharedPreferences.Editor ed;
    String  name;
    public int d,v,c,t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alltrain);
        d=v=c=t=0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.alltrainlay);
        DataAdapter1 adapter = new DataAdapter1(this, alltrainlay );
        recyclerView.setAdapter(adapter);
        String path = Environment.getExternalStorageDirectory().toString();
        toolbar.setTitleTextAppearance(this, R.style.RunannexFont);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView)header.findViewById(R.id.textView);
        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        name = sPref.getString("nam", "");
        textView.setText(name);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM HH:mm");
        String currentDateandTime = sdf.format(new Date());

        alltrainlay.add(new Phone1 (currentDateandTime, R.drawable.ic_done_black_24dp, d+" м.  "+t+" мин. "+c+" кал.  "+v+" c/c "));
        alltrainlay.add(new Phone1 (currentDateandTime, R.drawable.ic_done_black_24dp, d+" м.  "+t+" мин. "+c+" кал.  "+v+" c/c "));
        alltrainlay.add(new Phone1 (currentDateandTime, R.drawable.ic_done_black_24dp, d+" м.  "+t+" мин. "+c+" кал.  "+v+" c/c "));

        path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path + "/.Runannex/picture.png");
        ImageView imageView = (ImageView)header.findViewById(R.id.imageView);
        if (f.exists() && !f.isDirectory()) {
            imageView.setImageURI(Uri.parse(new File("file://" + path + "/.Runannex/picture.png").toString()));
        } else {
            imageView.setImageResource(R.drawable.ava);
        }
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_problem:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"slavafeatzhdos@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Ошибки");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, "Выбирите почту..."));
                    Toast.makeText(Alltrain.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Alltrain.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.train) {
            Intent i = new Intent(this, Training.class);
            startActivity(i);

        } else if (id == R.id.ach) {
            Intent i = new Intent(this, Ach.class);
            startActivity(i);


        } else if (id == R.id.stat) {
            Intent i = new Intent(this, Stata.class);
            startActivity(i);

        } else if (id == R.id.alltrain) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
