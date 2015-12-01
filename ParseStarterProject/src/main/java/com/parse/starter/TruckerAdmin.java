package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TruckerAdmin extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.fab_list)
    FloatingActionButton fabList;

    ParseQuery<ParseObject> query= ParseQuery.getQuery("Truckers");
    ParseUser u=ParseUser.getCurrentUser();


    ParseObject po;
    TruckersRecyclerAdapter adapter;
    List<ParseObject> list=new ArrayList<>();


    public static TruckerAdmin newInstance(){

        TruckerAdmin fragment = new TruckerAdmin();
        return fragment;
    }

    public TruckerAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_admin_truckers, container, false);
        ButterKnife.bind(this, rootView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter= new TruckersRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        query.whereEqualTo("idAdmin",u.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                for (ParseObject object : objects) {
                    list.add(object);
                }

                adapter.notifyDataSetChanged();
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        po = (ParseObject) view.getTag();//primer cambio

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Fragment menu = MenuFragment.newInstance(po.getObjectId());
                        ft.replace(R.id.flaContenido, menu);
                        ft.commit();

                    }
                });
            }
        });

        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
