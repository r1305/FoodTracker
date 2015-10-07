package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

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
    CircleImageView image;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ButterKnife.bind(this);
        toolbar.setTitle("Menu");
        toolbar.setNavigationIcon(R.drawable.menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ParseUser.getCurrentUser().getParseFile("foto").getDataInBackground(new GetDataCallback() {
//            @Override
//            public void done(byte[] data, com.parse.ParseException e) {
//
//                Utils.bit = BitmapFactory.decodeByteArray(data, 0, data.length);
//            }
//        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);

            }
        });
        image.setImageBitmap(Utils.bit);


        Fragment frag1 = ThirdFragment.newInstance();
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
                Fragment firstFragment =
                        FirstFragment.newInstance();

                ft.replace(R.id.flaContenido, firstFragment);

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

                Fragment thirdFragment =
                        ThirdFragment.newInstance();

                ft.replace(R.id.flaContenido, thirdFragment);
                ft.commit();
                dl.closeDrawers();
                return true;
        }

        return false;
    }




}
