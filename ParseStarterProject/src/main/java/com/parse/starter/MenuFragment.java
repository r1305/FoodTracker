package com.parse.starter;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MenuFragment extends Fragment{

    @Bind(R.id.recycler_view_menu)
    RecyclerView recyclerView;
    @Bind(R.id.return_fab)
    FloatingActionButton fab;

    public static MenuFragment newInstance(String objectId){
        MenuFragment fragment = new MenuFragment();
        Bundle b=new Bundle();
        b.putString("id", objectId);
        fragment.setArguments(b);
        return fragment;
    }

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        ButterKnife.bind(this, rootView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment list = ListTruckersFragment.newInstance();
                ft.replace(R.id.flaContenido, list);
                ft.commit();
            }
        });

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("menu");
        query.whereEqualTo("idTrucker", getArguments().getString("id"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                MenuRecyclerAdapter adapter = new MenuRecyclerAdapter(objects);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ParseObject po = (ParseObject) view.getTag();
                        FragmentTransaction ft=getFragmentManager().beginTransaction();
                        Fragment menu=DetailsFragment.newInstance(po.getObjectId(),getArguments().getString("id"));
                        ft.replace(R.id.flaContenido, menu);
                        ft.commit();
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

    }


}
