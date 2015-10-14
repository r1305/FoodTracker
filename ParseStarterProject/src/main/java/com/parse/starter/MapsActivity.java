package com.parse.starter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Bind(R.id.dl_maps)
    DrawerLayout dl;
    @Bind(R.id.toolbar_maps)
    Toolbar toolbar;

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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng total = new LatLng(-12.084756,-76.9730044);
        mMap.addMarker(new MarkerOptions().position(new LatLng(-12.084756, -76.9730044))
                .title("Ulima").snippet("Anticuchos").icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(-12.1323262, -76.9838797))
                .title("Richi").snippet("Empanadas").icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(-12.1316723, -76.9802212))
                .title("UPIG").snippet("Postres").icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_map)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(total, 11));
        ;
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
        UiSettings u=mMap.getUiSettings();
        u.setZoomControlsEnabled(true);
        u.setMapToolbarEnabled(true);

    }
    @Override
    public void onBackPressed(){
        MapsActivity.this.finish();
    }

}
