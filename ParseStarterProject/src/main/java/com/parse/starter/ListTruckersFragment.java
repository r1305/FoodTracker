package com.parse.starter;

import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListTruckersFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

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

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Truckers");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                TruckersRecyclerAdapter adapter = new TruckersRecyclerAdapter(objects);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParseObject po = (ParseObject) view.getTag();
                        Toast.makeText(getActivity(), po.getObjectId(), Toast.LENGTH_SHORT).show();

                        FragmentTransaction ft=getFragmentManager().beginTransaction();
                        Fragment menu=MenuFragment.newInstance(po.getObjectId());
                        ft.replace(R.id.flaContenido, menu);
                        ft.commit();

                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

    }


}
