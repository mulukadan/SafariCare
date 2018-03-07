package com.example.humungus.safaricare.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.humungus.safaricare.MainActivity;
import com.bumptech.glide.Glide;
import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.models.reportsModel;
import com.github.anastr.speedviewlib.AwesomeSpeedometer;
import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.util.OnSpeedChangeListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment implements LocationListener {

    private AwesomeSpeedometer mAwesomeSpeedometer;
    private FirebaseAuth mAuth;
    private Button reportBtn;
    private FirebaseUser currentUser = null;
    private FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();


    CircleImageView DpIV;

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //speedDometer code
        mAwesomeSpeedometer= (AwesomeSpeedometer) view.findViewById(R.id.awesomeSpeedometer);
        DpIV = (CircleImageView)view.findViewById(R.id.profile_image);

        try {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        LocationManager lm =(LocationManager) getActivity().getApplication().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.onLocationChanged(null);


//
        loadUserImag();

        reportBtn = (Button) view.findViewById(R.id.reportbtn);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    String thumbnail = currentUser.getPhotoUrl().toString() ;
                    String username = currentUser.getDisplayName().toString();
                    //Getting System Date
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String date = df.format(c);
                    String noPlate = "KCC 456D";
                    String MatName = "Transline";
                    String Sacco= "Transline Sacco";
                    String tweets = "Overlapping carelessly";

                    reportsModel newReport = new reportsModel(thumbnail, username, date, noPlate, MatName, Sacco, tweets);
                    DATABASE.getReference().child("reports").push().setValue(newReport);
                }
            }
        });

        return view;
    }




    @Override
    public void onLocationChanged(Location location) {
        if (location==null){
            // if you can't get speed because reasons :)
            mAwesomeSpeedometer.speedTo(0);
        }
        else{
            //int speed=(int) ((location.getSpeed()) is the standard which returns meters per second. In this example i converted it to kilometers per hour
            int speed= (int) (location.getSpeed()*3600/1000);
            mAwesomeSpeedometer.speedTo(speed);



            if(speed< 30){
                mAwesomeSpeedometer.setTrianglesColor(Color.GREEN);

            }else{
                mAwesomeSpeedometer.setTrianglesColor(Color.RED);
                Toast.makeText(getContext(), "Ogaa!!! You are Overspeeding,.. I will call the Police now", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
    private void loadUserImag() {
//        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(currentUser.getPhotoUrl().toString())
                        .into(DpIV);
            }
        }
    }
}
