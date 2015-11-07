package com.parse.starter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListTruckersFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    ParseQuery<ParseObject> query= ParseQuery.getQuery("Truckers");
    ParseObject po;
    TruckersRecyclerAdapter adapter;
    List<ParseObject> list=new ArrayList<>();


    public static ListTruckersFragment newInstance(){

        ListTruckersFragment fragment = new ListTruckersFragment();
        return fragment;
    }

    public ListTruckersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listtruckers, container, false);
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //el time out no me deja mostrar los truckers


        adapter= new TruckersRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
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
