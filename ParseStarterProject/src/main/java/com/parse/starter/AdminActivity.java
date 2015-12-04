package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.drawer_layout)
    DrawerLayout dl;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navigation)
    NavigationView nav;
    @Bind(R.id.profile)
    CircleImageView img;
    @Bind(R.id.txt_nav)
    TextView txt_nav;

    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.menu);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.animate();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
                if (dl.isDrawerOpen(GravityCompat.START)) {
                    dl.closeDrawers();
                }
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,dl,toolbar,R.string.openDrawer,
                R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        dl.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        nav.setNavigationItemSelectedListener(this);

        Fragment frag1 = TruckerAdmin.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.flaContenido, frag1);
        ft.commit();

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

                        Toast t= Toast.makeText(AdminActivity.this,e.toString(),Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });

        } else {

        }


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch(menuItem.getItemId()){
            case R.id.perfil:
                Fragment perfil =PerfilFragment.newInstance();

                ft.replace(R.id.flaContenido, perfil);
                toolbar.setTitle("Perfil");
                ft.commit();
                dl.closeDrawers();
                return true;

            case R.id.trucker:

                Fragment listado =TruckerAdmin.newInstance();

                ft.replace(R.id.flaContenido, listado);
                toolbar.setTitle("Truckers");
                ft.commit();
                dl.closeDrawers();
                return true;
            case R.id.logout:
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();
                Intent i=new Intent(AdminActivity.this,MainActivity.class);
                startActivity(i);
                AdminActivity.this.finish();
                return true;
            case R.id.maps:

                i=new Intent(AdminActivity.this,MapsActivity.class);
                startActivity(i);
                AdminActivity.this.finish();
                return true;

            case R.id.coupons:

                Fragment cupones =CuponesFragment.newInstance();

                ft.replace(R.id.flaContenido, cupones);
                toolbar.setTitle("Cupones");
                ft.commit();
                dl.closeDrawers();
                return true;
        }

        return false;
    }
}
