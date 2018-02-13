package com.example.admin.runannex;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    Intent myIntent;
    SharedPreferences sPref;
    Editor ed;
    private static final int PICK_IMAGE = 100;
    private ImageView imageView;

    @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                sPref = getApplicationContext().getSharedPreferences("Data", MODE_PRIVATE);
                ed = sPref.edit();

                if (sPref.getBoolean("firstrun", true)) {


                    final EditText weight = (EditText) findViewById(R.id.weight);
                    final EditText year = (EditText) findViewById(R.id.year);
                    final EditText name = (EditText) findViewById(R.id.name);
                    final EditText growth = (EditText) findViewById(R.id.growth);
                    final Button next = (Button) findViewById(R.id.next);
                    final TextView error = (TextView) findViewById(R.id.error);
                    name.requestFocus();
                    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    getSupportActionBar().setCustomView(R.layout.abs_layout);
                    error.setVisibility(View.INVISIBLE);
                    View.OnClickListener oclBtnOk = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int REQUEST_FINE_LOCATION = 112;
                            boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

                            if (!hasPermission) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_FINE_LOCATION);
                            } else {



                            int REQUEST_WRITE_STORAGE = 112;
                            boolean hasPermission2 = (ContextCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

                            if (!hasPermission2) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_WRITE_STORAGE);
                            } else {
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
                                        try {

                                            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                            File path = Environment.getExternalStorageDirectory();
                                            File dir = new File(path + "/.Runannex/");
                                            dir.mkdirs();
                                            File file = new File(dir, "picture.png");
                                            OutputStream out = null;
                                            out = new FileOutputStream(file);
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                            out.flush();
                                            out.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        closeActivity();


                                    } else {
                                        error.setText("Неправильныо введены данные");
                                        error.setVisibility(View.VISIBLE);
                                    }


                                }

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
        imageView = (ImageView) findViewById(R.id.image_view);

        Button pickImageButton = (Button) findViewById(R.id.pick_image_button);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int REQUEST_READ_STORAGE = 112;
                boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READ_STORAGE);
                } else {
                    openGallery();
                }
            }
        });
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
    private void closeActivity() {

        this.finish();

    }
}
