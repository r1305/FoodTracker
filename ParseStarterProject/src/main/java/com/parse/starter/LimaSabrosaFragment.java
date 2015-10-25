package com.parse.starter;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LimaSabrosaFragment extends Fragment {


    public static LimaSabrosaFragment newInstance(){

        LimaSabrosaFragment fragment = new LimaSabrosaFragment();
        return fragment;
    }


    public LimaSabrosaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lima_sabrosa, container, false);
    }


}
