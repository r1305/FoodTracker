package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    @Bind(R.id.toolbar_maps)
    Toolbar toolbar;
    LatLng total;
    @Bind(R.id.btn_buscar)
    Button buscar;
    @Bind(R.id.direccion)
    EditText direccion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, InicioActivity.class);
                startActivity(i);
                MapsActivity.this.finish();

            }
        });


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total=getLocationFromAddress(getBaseContext(),direccion.getText().toString());
                Toast.makeText(MapsActivity.this,"Latitud: "+total.latitude + ", Longitud: " +total.longitude,Toast.LENGTH_LONG).show();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(total, 18));
                mMap.addMarker(new MarkerOptions().position(total));
                direccion.getText().clear();
                direccion.clearFocus();

            }
        });



    }




    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        total = new LatLng(-12.084756,-76.9730044);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Truckers");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (final ParseObject object : objects) {
                        ParseGeoPoint geo = (ParseGeoPoint) object.get("location");
                        mMap.addMarker(new MarkerOptions().position(new LatLng(geo.getLatitude(), geo.getLongitude()))
                                .title(object.get("name").toString())
                                .snippet(object.get("tipo").toString())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)));
                    }

                }
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(total, 15));
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().
                        position(latLng));
            }
        });

        UiSettings u=mMap.getUiSettings();
        u.setZoomControlsEnabled(true);

    }


    @Override
    public void onBackPressed(){
        MapsActivity.this.finish();
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);

        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }



}
