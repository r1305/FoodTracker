package com.parse.starter;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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




    public static PerfilFragment newInstance(){

        return new PerfilFragment();
    }

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        ButterKnife.bind(this, rootView);

        ParseUser currentUser = ParseUser.getCurrentUser();


        if (currentUser != null) {
            txt.setText(currentUser.getString("name"));
            email.setText(currentUser.getEmail());

            ParseFile applicantResume = (ParseFile)currentUser.get("foto");
            applicantResume.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {

                        img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                    } else {
                        // something went wrong
                    }
                }
            });



        } else {
            // show the signup or login screen

        }



        return rootView;




    }


}
