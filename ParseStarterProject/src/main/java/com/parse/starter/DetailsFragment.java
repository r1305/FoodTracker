package com.parse.starter;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @Bind(R.id.foto)
    CircleImageView foto;
    @Bind(R.id.ingredientes)
    ListView ingredientes;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();

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
                precio.setText("     S/." + object.getNumber("precio").toString());

                JSONArray ing=object.getJSONArray("ingredientes");

                for(int i=0;i<ing.length();i++){
                    try {
                        System.out.println(ing.get(i).toString());
                        listItems.add(ing.get(i).toString());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems);

                ingredientes.setAdapter(adapter);

                try {
                    ParseFile applicantResume = (ParseFile) object.get("foto");
                    applicantResume.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {

                                foto.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                            } else {
                                // something went wrong
                                Toast t = Toast.makeText(getActivity(), "No se pudo obtener la foto", Toast.LENGTH_LONG);
                                t.show();

                            }
                        }
                    });
                }catch(Exception e1){
                    Toast t = Toast.makeText(getActivity(), "No se pudo obtener la foto", Toast.LENGTH_LONG);
                    t.show();
                }
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
