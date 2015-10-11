package com.parse.starter;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rogger on 05/10/2015.
 */
public class ListTruckersFragment extends Fragment {

    public static ListTruckersFragment newInstance(){
        return new ListTruckersFragment();
    }

    public ListTruckersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listtruckers, container, false);
    }

}
