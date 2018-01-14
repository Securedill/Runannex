package com.example.admin.runannex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                final EditText weight = (EditText) findViewById(R.id.weight);
                final EditText year = (EditText) findViewById(R.id.year);
                final EditText name = (EditText) findViewById(R.id.name);
                final EditText growth = (EditText) findViewById(R.id.growth);
                final Button next = (Button) findViewById(R.id.next);
                final TextView error = (TextView) findViewById(R.id.error);
                name.requestFocus();
                error.setVisibility(View.INVISIBLE);
                View.OnClickListener oclBtnOk = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Weight = weight.getText().toString();
                        String Name = name.getText().toString();
                        String Growth = growth.getText().toString();
                        String Year = year.getText().toString();
                        if ((Weight.length() == 0) || (Name.trim().length() ==0) || (Growth.trim().length() == 0) || (Year.trim().length() == 0)) {
                            error.setVisibility(View.VISIBLE);
                        } else {
                            error.setVisibility(View.INVISIBLE);
                            int g = Integer.parseInt(Growth);
                            int w = Integer.parseInt(Weight);
                            int y = Integer.parseInt(Year);

                            if (g>0 && y>0 && w>0 && Year.charAt(0) != '0' && Weight.charAt(0) != '0' && Growth.charAt(0) != '0') {
                                error.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(getApplicationContext(), Training.class);
                                MainActivity.this.startActivity(intent);
                            } else  { error.setText("Неправильныо введены данные");  error.setVisibility(View.VISIBLE);}


                        }

                    }
                };

                next.setOnClickListener(oclBtnOk);

            }
}
