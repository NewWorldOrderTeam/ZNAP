package com.znap.lmr.lmr_znap.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.znap.lmr.lmr_znap.R;

public class InfoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView description,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        description = (TextView) findViewById(R.id.description);
        title = (TextView) findViewById(R.id.title);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng downTown = new LatLng(49.841908, 24.031578);
        LatLng iVygovskogo = new LatLng(49.8257391, 23.9645272);
        LatLng shevchenka = new LatLng(49.869404, 23.949493);
        LatLng chyprunku = new LatLng(49.8271094, 24.0029683);
        LatLng levyckogo = new LatLng(49.832394, 24.044346);
        LatLng syhiv = new LatLng(49.789795, 24.055185);
        LatLng hvylyovogo = new LatLng(49.868492, 24.032518);
        mMap.addMarker(new MarkerOptions().position(downTown).title("Центр надання адміністративних послуг м. Львова").snippet("пл. Ринок, 1"));
        mMap.addMarker(new MarkerOptions().position(iVygovskogo).title("Територіальний підрозділ ЦНАП м. Львова на вул. І. Виговського, 32").snippet("вул. І. Виговського, 32, каб. № 101"));
        mMap.addMarker(new MarkerOptions().position(shevchenka).title("Територіальний підрозділ ЦНАП м. Львова на вул. Шевченка, 374").snippet("вул. Шевченка, 374"));
        mMap.addMarker(new MarkerOptions().position(chyprunku).title("Територіальний підрозділ ЦНАП м. Львова на вул. Ген. Чупринки, 85").snippet("вул. Ген. Чупринки, 85"));
        mMap.addMarker(new MarkerOptions().position(levyckogo).title("Територіальний підрозділ ЦНАП м. Львова на вул. К. Левицького, 67").snippet("вул. К. Левицького, 67, каб. № 100"));
        mMap.addMarker(new MarkerOptions().position(syhiv).title("Територіальний підрозділ ЦНАП м. Львова на пр. Червоної Калини, 72а").snippet("пр. Червоної Калини, 72а"));
        mMap.addMarker(new MarkerOptions().position(hvylyovogo).title("Територіальний підрозділ ЦНАП м. Львова на вул. М. Хвильового, 14а").snippet("вул. Хвильового, 14а"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(shevchenka));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(downTown,10));
    }
}

