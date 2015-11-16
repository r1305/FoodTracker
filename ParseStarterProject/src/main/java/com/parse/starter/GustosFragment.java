package com.parse.starter;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GustosFragment extends Fragment {

    @Bind(R.id.fab_gustos)
    FloatingActionButton fab;
    @Bind(R.id.list_gustos)
    ListView gustos;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public static GustosFragment newInstance(){
        return new GustosFragment();
    }

    public GustosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_gustos, container, false);
        ButterKnife.bind(this,root);


        ParseUser currentUser=ParseUser.getCurrentUser();

        JSONArray ing = currentUser.getJSONArray("gustos");

        if(ing!=null && ing.length()!=0) {
            for (int i = 0; i < ing.length(); i++) {
                try {
                    System.out.println(ing.get(i).toString());
                    listItems.add(ing.get(i).toString());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);

            gustos.setAdapter(adapter);
        }else{
            Snackbar.make(root,"Aun no ha registrado sus gustos",Snackbar.LENGTH_INDEFINITE).show();
            //Toast.makeText(getActivity(),"Aun no ha registrado sus gustos",Toast.LENGTH_LONG).show();
        }




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),GustosActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return root;

    }

}
