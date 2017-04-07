package com.sahilpaudel.app.findme;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.test.mock.MockPackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    Button findme;
    TextView textView, fullText;
    GPSTracker gps;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findme = (Button)findViewById(R.id.findme);
        textView = (TextView)findViewById(R.id.show);
        fullText = (TextView)findViewById(R.id.fullAddress);

        if (Build.VERSION.SDK_INT < 23) {

            findme.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    gps = new GPSTracker(MainActivity.this);
                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        textView.setText(latitude+"\n"+longitude);
                        getFullAddress(latitude,longitude);
                    }else{
                        gps.showSettingsAlert();
                    }
                }
            });

        }else {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission)
                        != PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{mPermission},
                            REQUEST_CODE_PERMISSION);
                    // If any permission above not allowed by user, this condition will
                    //execute every time, else your else part will work
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            findme.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    gps = new GPSTracker(MainActivity.this);
                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        Toast.makeText(MainActivity.this,latitude+"\n"+longitude , Toast.LENGTH_SHORT).show();
                        textView.setText(latitude+"\n"+longitude);
                        getFullAddress(latitude,longitude);
                    }else{
                        gps.showSettingsAlert();
                    }
                }
            });


        }
    }

    private void getFullAddress(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            String add = "";
            List<Address> address = geocoder.getFromLocation(latitude,longitude,1);
            if (address.size() > 0) {
                   add =   address.get(0).getSubAdminArea()+"\n"
                           +address.get(0).getAdminArea()+"\n"
                           +address.get(0).getCountryName()+"\n";
                fullText.setText(add);
            }
        }catch (IOException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
