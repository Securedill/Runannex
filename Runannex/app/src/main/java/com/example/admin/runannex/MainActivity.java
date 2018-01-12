package com.example.admin.runannex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText weight = (EditText) findViewById(R.id.weight);
        EditText years = (EditText) findViewById(R.id.years);
        EditText name = (EditText) findViewById(R.id.name);
        EditText growth = (EditText) findViewById(R.id.growth);
        Button next = (Button) findViewById(R.id.next);
        name.requestFocus();
    }

}
