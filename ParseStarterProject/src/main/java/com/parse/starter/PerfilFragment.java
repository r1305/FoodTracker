package com.parse.starter;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;


import butterknife.Bind;
import butterknife.ButterKnife;

public class PerfilFragment extends Fragment {
    @Bind(R.id.name_perfil)
    TextView txt;
    @Bind(R.id.img_perfil)
    ImageView img;
    @Bind(R.id.correo_perfil)
    TextView email;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.user_perfil)
    TextView user;





    public static PerfilFragment newInstance(){
        PerfilFragment fragment=new PerfilFragment();
        return fragment;
    }

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        ButterKnife.bind(this, rootView);

        final ParseUser currentUser = ParseUser.getCurrentUser();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(),EditProfileActivity.class);
                startActivity(i);
                getActivity().finish();

            }
        });


        if (currentUser != null) {
            txt.setText(currentUser.getString("name"));
            email.setText(currentUser.getEmail());
            user.setText(currentUser.getUsername());
            try {
                ParseFile applicantResume = (ParseFile) currentUser.get("foto");
                applicantResume.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {

                            img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                        } else {
                            // something went wrong
                            Toast t = Toast.makeText(getActivity(), "No se pudo obtener la foto", Toast.LENGTH_LONG);
                            t.show();

                        }
                    }
                });
            }catch(Exception e){
                Toast t = Toast.makeText(getActivity(), "No se pudo obtener la foto", Toast.LENGTH_LONG);
                t.show();
            }



        } else {
            // show the signup or login screen

        }



        return rootView;




    }


}
