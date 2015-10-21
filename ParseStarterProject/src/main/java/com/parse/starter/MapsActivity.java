package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    @Bind(R.id.dl_maps)
    DrawerLayout dl;
    @Bind(R.id.toolbar_maps)
    Toolbar toolbar;

    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.previous);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, Inicio.class);
                startActivity(i);
                MapsActivity.this.finish();

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng total = new LatLng(-12.084756,-76.9730044);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Truckers");
        query.getInBackground("WPQKCtbHou", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String info = "";
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-12.084756, -76.9730044))
                            .title(object.get("name").toString())
                            .snippet(object.get("tipo").toString() + info)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)));


                } else {
                    // something went wrong
                }
            }
        });
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Truckers");
        query1.getInBackground("gEQa5JtVCU", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-12.1323262, -76.9838797))
                            .title(object.get("name").toString())
                            .snippet(object.get("tipo").toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)));

                } else {
                    // something went wrong
                }
            }
        });
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Truckers");
        query2.getInBackground("5VVwOdjQFQ", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-12.1316723, -76.9802212))
                            .title(object.get("name").toString())
                            .snippet(object.get("tipo").toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)))
                    ;

                } else {
                    // something went wrong
                }
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(total, 11));
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);

        UiSettings u=mMap.getUiSettings();
        u.setZoomControlsEnabled(true);



    }
    @Override
    public void onBackPressed(){
        MapsActivity.this.finish();
    }



}
