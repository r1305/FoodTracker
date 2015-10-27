package com.parse.starter;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rogger on 27/10/2015.
 */
public class DetailsFragment extends Fragment {

    @Bind(R.id.fab_details)
    FloatingActionButton fab;
    @Bind(R.id.precio)
    TextView precio;
    @Bind(R.id.promo)
    TextView promo;
    @Bind(R.id.nombre)
    TextView nombre;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(String objectId,String truckId){
        DetailsFragment fragment = new DetailsFragment();
        Bundle b=new Bundle();
        b.putString("id", objectId);
        b.putString("idT",truckId);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, rootView);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("menu");
        query.getInBackground(getArguments().getString("id"), new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, ParseException e) {
                nombre.setText(object.getString("menu"));
                promo.setText(object.getString("oferta"));
                precio.setText(object.getNumber("precio").toString());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment list = MenuFragment.newInstance(getArguments().getString("idT"));
                ft.replace(R.id.flaContenido, list);
                ft.commit();
            }
        });





        return rootView;
    }



}
