package com.example.admin.runannex;


import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    ActionBar actionbar;
    TextView textview;
    LayoutParams layoutparams;
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
