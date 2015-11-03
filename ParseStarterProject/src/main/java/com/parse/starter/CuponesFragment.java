package com.parse.starter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rogger on 02/11/2015.
 */
public class CuponesFragment extends Fragment {

    public CuponesFragment() {
    }

    public static CuponesFragment newInstance(){
        CuponesFragment fragment = new CuponesFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
}
