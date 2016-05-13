package com.scan4us.scan4usgeotest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.scan4us.scan4usgeotest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo_scan4us);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setTitle("");
    }

    /**
     * metodo que implementa los clicks que se le hagan a las vistas del contexto actual
     * */
    public void clickBtons(View view){

        switch (view.getId()) {

            case R.id.bton_phone:
                startActivity(new Intent(getApplicationContext(), ManagerPhotoActivity.class));
                break;

            case R.id.bton_pulse:
                startActivity(new Intent(getApplicationContext(),HeartPulseActivity.class));
                break;
            case R.id.bton_gps:
                startActivity(new Intent(getApplicationContext(),MapActivity.class));
                break;
            default:

                break;

        }
    }

}
