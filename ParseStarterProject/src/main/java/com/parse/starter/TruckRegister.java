package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import Utilities.BitmapToByteArray;
import Utilities.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TruckRegister extends AppCompatActivity {

    @Bind(R.id.save_fab_trucker)
    FloatingActionButton save_truck;
    @Bind(R.id.name_ft)
    EditText name_ft;
    @Bind(R.id.tipo_ft)
    EditText tipo_ft;
    @Bind(R.id.dir_ft)
    EditText dir_ft;
    @Bind(R.id.toolbar_truck_register)
    Toolbar toolbar;
    @Bind(R.id.img_truck)
    CircleImageView img_truck;
    int PICK_IMAGE=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_register);
        ButterKnife.bind(this);

        final Bitmap bitmap = ((BitmapDrawable) img_truck.getDrawable()).getBitmap();
        new BitmapToByteArray().execute(bitmap);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("Registrando");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TruckRegister.this, AdminActivity.class);
                startActivity(i);
                TruckRegister.this.finish();

            }
        });

        img_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        save_truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LatLng latLng=getLocationFromAddress(getBaseContext(),dir_ft.getText().toString());

                final ParseFile foto = new ParseFile("foto.png", Utils.imageBuffer);
                foto.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        ParseObject o=new ParseObject("Truckers");
                        o.put("foto", foto);
                        o.put("name", name_ft.getText().toString());
                        o.put("tipo", tipo_ft.getText().toString());
                        o.put("direccion", dir_ft.getText().toString());
                        o.put("idAdmin", ParseUser.getCurrentUser().getObjectId());
                        ParseGeoPoint pg=new ParseGeoPoint(latLng.latitude,latLng.longitude);
                        o.put("location",pg);

                        o.saveInBackground(new SaveCallback() {

                            @Override
                            public void done(ParseException e) {

                                Intent i = new Intent(TruckRegister.this, AdminActivity.class);
                                startActivity(i);

                                Toast t = Toast.makeText(TruckRegister.this, "Edicion exitosa", Toast.LENGTH_SHORT);
                                t.show();
                                TruckRegister.this.finish();


                            }
                        });
                    }
                });

            }
        });
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            img_truck.setImageURI(imageUri);
            Bitmap bitmap = ((BitmapDrawable) img_truck.getDrawable()).getBitmap();
            new BitmapToByteArray().execute(bitmap);
        }
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
