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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                    StringBuffer gustos=new StringBuffer();
                    if(hamb.isChecked()){
                        gustos.append(hamb.getText().toString()+",");

                    }else if(salchi.isChecked()){
                        gustos.append(salchi.getText().toString()+",");
                    }else if(pizza.isChecked()){
                        gustos.append(pizza.getText().toString()+",");
                    }else if(piqueo.isChecked()){
                        gustos.append(piqueo.getText().toString()+",");
                    }else if(criollo.isChecked()){
                        gustos.append(criollo.getText().toString()+",");
                    }else if(orga.isChecked()){
                        gustos.append(orga.getText().toString()+",");
                    }else if(carnes.isChecked()){
                        gustos.append(carnes.getText().toString()+",");
                    }else if(alitas.isChecked()) {
                        gustos.append(alitas.getText().toString()+",");
                    }else if(tacos.isChecked()){
                        gustos.append(tacos.getText().toString()+",");
                    }else{
                        gustos.append(postres.getText().toString()+",");
                    }



                    currentUser.addAllUnique("gustos", Arrays.asList("Salchipapa","Hamburguesa"));
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast t = Toast.makeText(GustosActivity.this, "Gustos registrado", Toast.LENGTH_LONG);
                            t.show();
                            Intent i = new Intent(GustosActivity.this, Inicio.class);
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
