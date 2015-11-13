package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import org.json.simple.JSONArray;


import butterknife.Bind;
import butterknife.ButterKnife;

public class GustosActivity extends AppCompatActivity {

    @Bind(R.id.btn_gustos)Button save_gustos;
    @Bind(R.id.criollo)CheckBox criollo;
    @Bind(R.id.piqueoM)CheckBox piqueo;
    @Bind(R.id.hamburguesas)CheckBox hamb;
    @Bind(R.id.salchipapas)CheckBox salchi;
    @Bind(R.id.organico)CheckBox orga;
    @Bind(R.id.carnes)CheckBox carnes;
    @Bind(R.id.pizza)CheckBox pizza;
    @Bind(R.id.alitas)CheckBox alitas;
    @Bind(R.id.tacos)CheckBox tacos;
    @Bind(R.id.postres)CheckBox postres;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gustos_register);
        ButterKnife.bind(this);




        save_gustos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {


                    JSONArray l=new JSONArray();
                    if(hamb.isChecked()){

                        l.add(hamb.getText().toString());

                    }
                    if(salchi.isChecked()){

                        l.add(salchi.getText().toString());
                    }
                    if(pizza.isChecked()){

                        l.add(pizza.getText().toString());
                    }
                    if(piqueo.isChecked()){

                        l.add(piqueo.getText().toString());
                    }
                    if(criollo.isChecked()){

                        l.add(criollo.getText().toString());
                    }
                    if(orga.isChecked()){

                        l.add(orga.getText().toString());
                    }
                    if(carnes.isChecked()){

                        l.add(carnes.getText().toString());

                    }
                    if(alitas.isChecked()) {

                        l.add(alitas.getText().toString());
                    }
                    if(tacos.isChecked()){

                        l.add(tacos.getText().toString());
                    }
                    if(postres.isChecked()){

                        l.add(postres.getText().toString());
                    }
                    currentUser.remove("gustos");
                    currentUser.addAllUnique("gustos",l);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast t = Toast.makeText(GustosActivity.this, "Gustos registrado", Toast.LENGTH_LONG);
                            t.show();
                            Intent i = new Intent(GustosActivity.this, InicioActivity.class);
                            startActivity(i);
                            GustosActivity.this.finish();
                        }
                    });

                } else {
                    // show the signup or login screen
                }

            }
        });
    }
}
