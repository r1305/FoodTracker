package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity {
    @Bind(R.id.register_okBtn)
    Button ok;
    @Bind(R.id.register_cancelBtn)
    Button cancel;
    @Bind(R.id.nameTxt)EditText name;
    @Bind(R.id.emailTxt)EditText email;
    @Bind(R.id.passwordTxt)EditText psw;
    @Bind(R.id.sexGroup)RadioGroup sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();
                user.setUsername(email.getText().toString());
                user.setPassword(psw.getText().toString());
                user.setEmail(email.getText().toString());
                user.put("name",name.getText().toString());

                switch (sex.getCheckedRadioButtonId()){
                    case R.id.masculino:
                        user.put("sexo","masculino");
                        break;
                    case R.id.femenino:
                        user.put("sexo","femenino");
                        break;
                }

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(Register.this, Inicio.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("Algo esta mal",e.getMessage());
                            Context context = getApplicationContext();
                            CharSequence text = "Algo salio mal!!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
