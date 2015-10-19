package com.parse.starter;

import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListTruckersFragment extends Fragment {

    public static ListTruckersFragment newInstance(){

        ListTruckersFragment fragment = new ListTruckersFragment();
        return fragment;
    }

    public ListTruckersFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.truck1)TextView t1;
    @Bind(R.id.truck2)TextView t2;
    @Bind(R.id.truck3)TextView t3;
    @Bind(R.id.img_truck1)ImageView img_t1;
    @Bind(R.id.img_truck2)ImageView img_t2;
    @Bind(R.id.img_truck3)ImageView img_t3;
    @Bind(R.id.tipo1)TextView tp1;
    @Bind(R.id.tipo2)TextView tp2;
    @Bind(R.id.tipo3)TextView tp3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_listtruckers, container, false);

        ButterKnife.bind(this,rootView);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Truckers");
        query.getInBackground("WPQKCtbHou", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    t1.setText(object.getString("name").toString());
                    tp1.setText(object.getString("tipo").toString());
                    ParseFile applicantResume = (ParseFile) object.get("foto");

                    applicantResume.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {

                                img_t1.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                            } else {
                                // something went wrong

                                Toast t = Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
                                t.show();
                            }
                        }
                    });

                } else {
                    // something went wrong
                }
            }
        });

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Truckers");
        query1.getInBackground("gEQa5JtVCU", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    t2.setText(object.getString("name").toString());
                    tp2.setText(object.getString("tipo").toString());
                    ParseFile applicantResume = (ParseFile)object.get("foto");

                    applicantResume.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {

                                img_t2.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                            } else {
                                // something went wrong

                                Toast t= Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG);
                                t.show();
                            }
                        }
                    });

                } else {
                    // something went wrong
                }
            }
        });


        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Truckers");
        query2.getInBackground("5VVwOdjQFQ", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    t3.setText(object.getString("name").toString());
                    tp3.setText(object.getString("tipo").toString());
                    ParseFile applicantResume = (ParseFile)object.get("foto");

                    applicantResume.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {

                                img_t3.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                            } else {
                                // something went wrong

                                Toast t= Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG);
                                t.show();
                            }
                        }
                    });

                } else {
                    // something went wrong
                }
            }
        });


        return rootView;
    }



}
