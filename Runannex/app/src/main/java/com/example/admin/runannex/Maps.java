package com.example.admin.runannex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Maps extends AppCompatActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;
        map.getUiSettings().setAllGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

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
                    Toast.makeText(Maps.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) { Toast.makeText(Maps.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show(); }
                //Toast.makeText(Result.this, "Спасибо за помощь", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.info:

                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
