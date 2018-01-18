package com.example.admin.runannex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    Intent myIntent;
    SharedPreferences sPref;
    Editor ed;
    ImageView imageView;
     final int Pick_image = 1;
    @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                sPref = getApplicationContext().getSharedPreferences("Data", MODE_PRIVATE);
                ed = sPref.edit();
                if (sPref.getBoolean("firstrun", true)) {

                    final EditText weight = (EditText) findViewById(R.id.weight);
                    final EditText year = (EditText) findViewById(R.id.year);
                    final EditText name = (EditText) findViewById(R.id.name);
                    final EditText growth = (EditText) findViewById(R.id.growth);
                    final Button next = (Button) findViewById(R.id.next);
                    final TextView error = (TextView) findViewById(R.id.error);
                    imageView = (ImageView)findViewById(R.id.image);
                    Button PickImage = (Button) findViewById(R.id.button);
                    PickImage.setVisibility(View.VISIBLE);
                    PickImage.setBackgroundColor(Color.TRANSPARENT);
                    PickImage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, Pick_image);
                        }
                    });



                    name.requestFocus();
                    error.setVisibility(View.INVISIBLE);
                    View.OnClickListener oclBtnOk = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Weight = weight.getText().toString();
                            String Name = name.getText().toString();
                            String Growth = growth.getText().toString();
                            String Year = year.getText().toString();
                            if ((Weight.length() == 0) || (Name.trim().length() == 0) || (Growth.trim().length() == 0) || (Year.trim().length() == 0)) {
                                error.setVisibility(View.VISIBLE);
                            } else {
                                error.setVisibility(View.INVISIBLE);

                                if (Year.charAt(0) != '-' && Weight.charAt(0) != '-' && Growth.charAt(0) != '-' && Year.charAt(0) != '0' && Weight.charAt(0) != '0' && Growth.charAt(0) != '0') {
                                    error.setVisibility(View.INVISIBLE);
                                    ed.putString("yea", Year);
                                    ed.putString("growt", Growth);
                                    ed.putString("weigh", Weight);
                                    ed.putString("nam", Name);
                                    ed.commit();
                                     myIntent = new Intent(v.getContext(), Training.class);
                                    startActivity(myIntent);
                                    sPref.edit().putBoolean("firstrun", false).commit();
                                    closeActivity();


                                } else {
                                    error.setText("Неправильныо введены данные");
                                    error.setVisibility(View.VISIBLE);
                                }


                            }

                        }

                    };
                    next.setOnClickListener(oclBtnOk);
                } else {
                    Intent intent = new Intent(MainActivity.this, Training.class);
                    startActivity(intent);
                    closeActivity();


                }

            }
    private void closeActivity() {

        this.finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}
}
