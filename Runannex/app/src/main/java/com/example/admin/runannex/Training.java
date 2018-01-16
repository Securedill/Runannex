package com.example.admin.runannex;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Training extends AppCompatActivity {
    SharedPreferences sPref;
    EditText test;
    String weight,year,growth,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        sPref = getApplication().getSharedPreferences("Data", MODE_PRIVATE);
        weight = sPref.getString("weigh", "");
        name = sPref.getString("nam", "");
        growth = sPref.getString("growt", "");
        year = sPref.getString("yea", "");




    }
}