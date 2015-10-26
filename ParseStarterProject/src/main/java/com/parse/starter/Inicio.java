package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.parse.starter.R.drawable.abc_ic_ab_back_mtrl_am_alpha;


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
    FloatingActionButton fab;







    ParseUser currentUser = ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.menu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
                
            }
        });


        if (currentUser != null) {
            txt_nav.setText(currentUser.getString("name"));

            ParseFile applicantResume = (ParseFile)currentUser.get("foto");

            applicantResume.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {

                        img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment frag1 = PerfilFragment.newInstance();
                                FragmentTransaction ft1 = getFragmentManager().beginTransaction();

                                ft1.replace(R.id.flaContenido, frag1);
                                ft1.commit();
                                dl.closeDrawers();
                            }
                        });

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
                toolbar.setTitle("Perfil");
                ft.commit();
                dl.closeDrawers();
                return true;
            case R.id.gustos:
                Fragment secondFragment =
                        GustosFragment.newInstance();

                ft.replace(R.id.flaContenido, secondFragment);
                toolbar.setTitle("Gustos");
                ft.commit();
                dl.closeDrawers();
                return true;
            case R.id.trucker:

                Fragment listado =
                        ListTruckersFragment.newInstance();

                ft.replace(R.id.flaContenido, listado);
                toolbar.setTitle("Truckers");
                ft.commit();
                dl.closeDrawers();
                return true;
            case R.id.logout:
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();
                Intent i=new Intent(Inicio.this,MainActivity.class);
                startActivity(i);
                Inicio.this.finish();
                return true;
            case R.id.maps:

                i=new Intent(Inicio.this,MapsActivity.class);
                startActivity(i);
                Inicio.this.finish();
                return true;

            case R.id.fab:


                i=new Intent(Inicio.this,EditProfileActivity.class);
                startActivity(i);
                Inicio.this.finish();
                return true;


        }

        return false;
    }




}
