package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import Utilities.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.FbLoginBtn) Button FbLoginBtn;
    @Bind(R.id.registrar) Button registrar;
    @Bind(R.id.login_email)EditText email;
    @Bind(R.id.login_password)EditText psw;
    @Bind(R.id.login)Button log;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      ButterKnife.bind(this);

      registrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(MainActivity.this, Register.class);
              startActivity(intent);

          }
      });
      log.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {

              String username = email.getText().toString().toLowerCase();
              String pass = psw.getText().toString();
              ParseUser.logInInBackground(username, pass, new LogInCallback() {
                  public void done(ParseUser user, ParseException e) {
                      if (user != null) {
                          Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(MainActivity.this, Inicio.class);
                          startActivity(intent);

                      } else {
                          // Signup failed. Look at the ParseException to see what happened.
                          Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                      }

                  }
              });

          }

      });

      ParseAnalytics.trackAppOpenedInBackground(getIntent());
      FbLoginBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              List<String> permissions = Arrays.asList("public_profile", "email");
              ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, permissions, new LogInCallback() {
                  @Override
                  public void done(ParseUser user, ParseException err) {
                      if (err == null) {

                          if (user == null) {
                              Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");

                          } else if (user.isNew()) {
                              Log.d("MyApp", "User signed up and logged in through Facebook!");
                              Utils.parseUser=user;
                              Intent intent = new Intent(MainActivity.this, Register.class);
                              startActivity(intent);
                              Utils.user.setFacebook(true);

                          } else {
                              Log.d("MyApp", "User logged in through Facebook!");


                              Intent intent = new Intent(MainActivity.this, Inicio.class);
                              startActivity(intent);
                              Toast t=Toast.makeText(MainActivity.this,"Login de Fb correcto",Toast.LENGTH_LONG);
                              t.show();
                              Utils.user.setFacebook(true);
                          }
                      } else {
                          Log.e("ErrorFB", err.getMessage().toString());
                      }


                  }
              });
          }
      });
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
