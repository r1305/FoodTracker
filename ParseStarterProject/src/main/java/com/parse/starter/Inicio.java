package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import Utilities.DownloadImageTask;
import Utilities.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.drawer_layout)
    DrawerLayout dl;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navigation)
    NavigationView nav;
    @Bind(R.id.profile_image)
    CircleImageView img;
    @Bind(R.id.txt_nav)
    TextView txt_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ButterKnife.bind(this);
        toolbar.setTitle("Menu");
        toolbar.setNavigationIcon(R.drawable.menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);

            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            txt_nav.setText(currentUser.getString("name"));

            ParseFile applicantResume = (ParseFile)currentUser.get("foto");
            applicantResume.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {

                        img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                    } else {
                        // something went wrong

                        Toast t= Toast.makeText(Inicio.this,e.toString(),Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });



        } else {
            // show the signup or login screen

        }


        Fragment frag1 = ListTruckersFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.flaContenido, frag1);
        ft.commit();

        nav.setNavigationItemSelectedListener(this);

    }





    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch(menuItem.getItemId()){
            case R.id.perfil:
                Fragment perfil =
                        PerfilFragment.newInstance();

                ft.replace(R.id.flaContenido, perfil);

                ft.commit();
                dl.closeDrawers();
                return true;
            case R.id.gustos:
                Fragment secondFragment =
                        SecondFragment.newInstance();

                ft.replace(R.id.flaContenido, secondFragment);
                ft.commit();
                dl.closeDrawers();
                return true;
            case R.id.trucker:

                Fragment listado =
                        ListTruckersFragment.newInstance();

                ft.replace(R.id.flaContenido, listado);
                ft.commit();
                dl.closeDrawers();
                return true;
        }

        return false;
    }




}
