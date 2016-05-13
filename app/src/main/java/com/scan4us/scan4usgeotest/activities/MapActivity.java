package com.scan4us.scan4usgeotest.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.scan4us.scan4usgeotest.LocationHelper;
import com.scan4us.scan4usgeotest.R;

/**
 * Created by juanc on 5/05/2016.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    TextView tvLatitude, tvLongitude;
    Button btSeek;
    GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        btSeek = (Button) findViewById(R.id.btSeek);
        btSeek.setEnabled(false);

        btSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationHelper helper = new LocationHelper();
                LocationHelper.LocationResult result = new LocationHelper.LocationResult() {
                    @Override
                    public void gotLocation(Location location) {
                        if (location != null) {
                            Toast.makeText(getApplicationContext(), location.getProvider(), Toast.LENGTH_SHORT).show();
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .title("Tu ubicación :)"));

                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 7);
                            googleMap.animateCamera(cameraUpdate);

                            tvLatitude.setText(String.valueOf(location.getLatitude()));
                            tvLongitude.setText(String.valueOf(location.getLongitude()));

                        } else{
                            Toast.makeText(getApplicationContext(), "Error obteniendo tu ubicaicon, verifica que tu GPS este encendido", Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                helper.getLocation(getApplicationContext(), result);

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.btSeek.setEnabled(true);
    }
}
