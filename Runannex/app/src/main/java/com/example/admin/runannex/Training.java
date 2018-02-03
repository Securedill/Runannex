package com.example.admin.runannex;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import java.io.File;



public class Training extends AppCompatActivity {
    SharedPreferences sPref;
    String weight,year,growth,name;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        weight = sPref.getString("weigh", "");
        name = sPref.getString("nam", "");
        growth = sPref.getString("growt", "");
        year = sPref.getString("yea", "");
        imageView = (ImageView) findViewById(R.id.image_view);
        String path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path + "/.Runannex/picture.png");



        /* if(f.exists() && !f.isDirectory()) {
            imageView.setImageURI(Uri.parse(new File("file://" + path + "/.Runannex/picture.png").toString()));
        } else { imageView.setImageResource(R.drawable.ava);} */



    }




    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}